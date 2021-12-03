package com.dama.DamA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.ActivityDetailCafeBinding
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class DetailCafeActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: ActivityDetailCafeBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityDetailCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.DetailCafeViewToolbarTb).apply {
            title = "내카페"
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewPager2 = findViewById(R.id.DetailCafeView_viewPager_vp)

        viewPager2.adapter = ViewPagerAdapterClass(this)

        val indicator = binding.DetailCafeViewIndicatorDi
        indicator.setViewPager2(binding.DetailCafeViewViewPagerVp)




        database = Firebase.database.reference
        var cafe_name = binding.DetailCafeViewNameTv
        var cafe_subname = binding.DetailCafeViewSubNameTv
        var cafe_location = binding.DetailCafeViewLocationTv
        var cafe_call = binding.DetailCafeViewCallTv
        var cafe_runtime = binding.DetailCafeViewRuntimeTv
        var cafe_facility = binding.DetailCafeViewFacilityTv
        var cafe_tumbler = binding.DetailCafeViewRentalTumblerTv
        var cafe_uid=""
        val ownerUid=intent.getStringExtra("ownerUid").toString()
        database.child("cafe").child(ownerUid).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            if(it.exists()){
                var cafeData=it.getValue<Cafe>()
                cafe_uid=cafeData?.ownerUid.toString()
                cafe_name.setText(cafeData?.cafeName.toString())
                cafe_subname.setText(cafeData?.cafeSubName.toString())
                cafe_location.setText(cafeData?.location.toString())
                cafe_call.setText(cafeData?.call.toString())
                cafe_runtime.setText(cafeData?.runtime.toString())
                cafe_facility.setText(cafeData?.facility.toString())
                var tumbler=0
                if(cafeData?.rentalTumbler==null) {
                    cafe_tumbler.setText(cafeData?.totalTumbler!!.toString())
                }
                else{
                   tumbler=cafeData?.rentalTumbler!!.toInt()
                }
                cafe_tumbler.setText((cafeData?.totalTumbler!!.toInt()-tumbler).toString())

            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }



        //빌리기 버튼
        binding.DetailCafeViewRentalBtn.setOnClickListener{
            FirebaseDB().writeRentalRequest(
                cafe_uid,Firebase.auth.currentUser?.uid.toString())
        }

        //반납하기 버튼
        binding.DetailCafeViewReturnBtn.setOnClickListener{
            FirebaseDB().writeReturnRequest(
                cafe_uid,Firebase.auth.currentUser?.uid.toString())
        }

    }
}