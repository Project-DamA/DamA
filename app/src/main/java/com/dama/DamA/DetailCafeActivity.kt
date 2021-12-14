package com.dama.DamA

import android.graphics.Color
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.ActivityDetailCafeBinding
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class DetailCafeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCafeBinding
    private lateinit var database: DatabaseReference
    private lateinit var requestType: String
    private lateinit var cafeImageList:ArrayList<Uri>
    override fun onCreate(savedInstanceState: Bundle?) {

        requestType = ""
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }





        binding.DetailCafeViewRequestBtn.isEnabled = false
        database = Firebase.database.reference
        var cafe_name = binding.DetailCafeViewNameTv
        var cafe_subname = binding.DetailCafeViewSubNameTv
        var cafe_location = binding.DetailCafeViewLocationTv
        var cafe_call = binding.DetailCafeViewCallTv
        var cafe_runtime = binding.DetailCafeViewRuntimeTv
        var cafe_facility = binding.DetailCafeViewFacilityTv
        var cafe_tumbler = binding.DetailCafeViewRentalTumblerTv
        var cafe_uid = ""
        val ownerUid = intent.getStringExtra("ownerUid").toString()


        cafeImageList= arrayListOf()
        getImagesViewpager(ownerUid)
        database.child("cafe").child(ownerUid).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            if (it.exists()) {
                var cafeData = it.getValue<Cafe>()
                cafe_uid = cafeData?.ownerUid.toString()
                cafe_name.text = cafeData?.cafeName.toString()
                cafe_subname.text = cafeData?.cafeSubName.toString()
                cafe_location.text = cafeData?.location.toString()
                cafe_call.text = cafeData?.call.toString()
                cafe_runtime.text = cafeData?.runtime.toString()
                cafe_facility.text = cafeData?.facility.toString()
                var tumbler = 0
                if (cafeData?.rentalTumbler == null) {
                    cafe_tumbler.setText(cafeData?.totalTumbler!!.toString())
                } else {
                    tumbler = cafeData.rentalTumbler!!.toInt()
                }
                cafe_tumbler.text = (cafeData.totalTumbler!!.toInt() - tumbler).toString()
                showButton(cafeData, ownerUid)
            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }


        //빌리기 반납 버튼
        binding.DetailCafeViewRequestBtn.setOnClickListener {
            FirebaseDB().writeRequest(cafe_uid, requestType)
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }


    }

    private fun showButton(cafe: Cafe, ownerUid: String) {
        val userUid = Firebase.auth.currentUser!!.uid

        database.child("request").child(ownerUid).child("rental").get()
            .addOnSuccessListener { rentalSnapshot ->
                //대여 신청 중
                if (rentalSnapshot.hasChild(userUid)) {//rental에 리퀘스트가 있음
                    binding.DetailCafeViewRequestBtn.text = "빌리기"
                    binding.DetailCafeViewRequestBtn.setBackgroundColor(Color.GRAY)
                    binding.DetailCafeViewRequestBtn.isEnabled = false
                } else {//rental에 리퀘스트가 없음
                    database.child("request").child(ownerUid).child("return").get()
                        .addOnSuccessListener { returnSnapshot ->
                            //반납 신청 중
                            if (returnSnapshot.hasChild(userUid)) {//return에 리퀘스트가 있음
                                binding.DetailCafeViewRequestBtn.text = "반납하기"
                                binding.DetailCafeViewRequestBtn.setBackgroundColor(Color.GRAY)
                                binding.DetailCafeViewRequestBtn.isEnabled = false
                            }

                            else {//return에 리퀘스트가 없음
                                //반납 가능

                                if(cafe.rentalUsers!=null) {
                                    if (cafe.rentalUsers.containsValue(userUid)) {
                                        binding.DetailCafeViewRequestBtn.text = "반납하기"
                                        binding.DetailCafeViewRequestBtn.isEnabled = true
                                        requestType = "return"

                                    } else {
                                        //대여 가능
                                        binding.DetailCafeViewRequestBtn.text = "빌리기"
                                        binding.DetailCafeViewRequestBtn.isEnabled = true
                                        requestType = "rental"
                                    }
                                }
                                else{
                                    //대여 가능
                                    binding.DetailCafeViewRequestBtn.text = "빌리기"
                                    binding.DetailCafeViewRequestBtn.isEnabled = true
                                    requestType = "rental"
                                }
                            }
                        }


                }
            }



        Log.d("requestType", requestType)
    }
    private fun getImagesViewpager(uid:String) {
        Firebase.storage.reference.child("cafe_images").child(uid).listAll().addOnSuccessListener { itemsList ->
            itemsList.items.forEach { item ->
                item.downloadUrl.addOnSuccessListener {
                    cafeImageList.add(it)
                    binding.DetailCafeViewViewPagerVp.adapter=ImageListAdapter(cafeImageList,this)
                    binding.DetailCafeViewViewPagerVp.orientation=ViewPager2.ORIENTATION_HORIZONTAL

                    // indicator 생성
                    val indicator = binding.DetailCafeViewIndicatorDi
                    indicator.setViewPager2(binding.DetailCafeViewViewPagerVp)
                }
            }

        }
    }
}