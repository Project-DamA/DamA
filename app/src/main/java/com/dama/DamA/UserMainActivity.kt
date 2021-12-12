package com.dama.DamA


import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.ActivityUserMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.collections.ArrayList


class UserMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserMainBinding
    private lateinit var cafeList: ArrayList<Cafe>
    private lateinit var dbref: DatabaseReference
    private lateinit var cafeImageList: ArrayList<Uri>
    private lateinit var cafeOwnerUid: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cafeOwnerUid=""
        cafeList = arrayListOf()
        cafeImageList = arrayListOf()
        getUserData()
        getCafeData()

        val rentalTimeString = intent.getStringExtra("userRentalTime")
        if (rentalTimeString == null) {
            binding.UserMainViewViewpagerVp.visibility = View.INVISIBLE
        }

//        val rentalTimeString = intent.getStringExtra("userRentalTime")
//        if (rentalTimeString != null) {
//            val userRentalTime =
//                LocalDateTime.parse(rentalTimeString, DateTimeFormatter.ISO_DATE_TIME).plusDays(7)
//
//            val current = LocalDateTime.now()
//            val duration = Duration.between(userRentalTime, current).seconds
//            binding.UserMainViewReturnTimeTxt.setText("${toString()} 남았습니다.")
//
//        }


        //더보기 버튼
        binding.UserMainViewMoreIb.setOnClickListener {
            startActivity(Intent(this, UserMenuActivity::class.java))
        }


    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("users")
            .child(Firebase.auth.currentUser!!.uid)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val user = snapshot.getValue<User>()

                if (user!!.rentalCafe != null) {

                    binding.UserMainViewDetailBtn.setOnClickListener {
                        val i=Intent(parent, DetailCafeActivity::class.java)
                        i.putExtra("ownerUid",user.rentalCafe)
                        startActivity(i)
                    }

                    cafeOwnerUid=user.rentalCafe.toString()
                    getImagesViewpager(cafeOwnerUid)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getCafeData() {
        dbref = FirebaseDatabase.getInstance().getReference("cafe")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (cafeSnapshot in snapshot.children) {

                    val cafe = cafeSnapshot.getValue<Cafe>()
                    cafeList.add(cafe!!)
                    if(cafe.ownerUid==cafeOwnerUid){
                        binding.UserMainViewNameTv.text=cafe.cafeName
                    }

                }
                binding.CafeList.adapter = CafeListAdapter(this@UserMainActivity, cafeList)
                binding.CafeList.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

    fun gotoDetail(uid: String) {
        val i = Intent(this@UserMainActivity, DetailCafeActivity::class.java)
        i.putExtra("ownerUid", uid)
        startActivity(i)
    }

    private fun getImagesViewpager(uid: String) {
        Firebase.storage.reference.child("cafe_images").child(uid).listAll()
            .addOnSuccessListener { itemsList ->
                itemsList.items.forEach { item ->
                    item.downloadUrl.addOnSuccessListener {
                        cafeImageList.add(it)
                        binding.UserMainViewViewpagerVp.adapter =
                            ImageListAdapter(cafeImageList, this)
                        binding.UserMainViewViewpagerVp.orientation =
                            ViewPager2.ORIENTATION_HORIZONTAL

                        // indicator 생성
                        val indicator = binding.UserMainViewIndicatorDi
                        indicator.setViewPager2(binding.UserMainViewViewpagerVp)
                    }
                }

            }
    }
}