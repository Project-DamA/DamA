package com.dama.DamA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.ActivitySettingCafeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class SettingCafeActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: ActivitySettingCafeBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        database = Firebase.database.reference

        super.onCreate(savedInstanceState)
        binding = ActivitySettingCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewPager2 = findViewById(R.id.SettingCafeView_viewPager_vp)

        viewPager2.adapter = ViewPagerAdapterClass(this)

        val indicator = binding.SettingCafeViewIndicatorDi
        indicator.setViewPager2(binding.SettingCafeViewViewPagerVp)


        var cafe_name = binding.SettingCafeViewNameEt
        var cafe_subname = binding.SettingCafeViewSubNameEt
        var cafe_location = binding.SettingCafeViewLocationEv
        var cafe_call = binding.SettingCafeViewPhoneEv
        var cafe_runtime = binding.SettingCafeViewTimeEv
        var cafe_facility = binding.SettingCafeViewFacilityEv
        var cafe_tumbler = binding.SettingCafeViewRentaltumblerEv


        database.child("cafe").child(Firebase.auth.currentUser?.uid.toString()).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            setSupportActionBar(binding.SettingCafeViewToolbarTb).apply {
                title=it.getValue<Cafe>()?.cafeName.toString()
            }
            cafe_name.setText(it.getValue<Cafe>()?.cafeName.toString())
            cafe_subname.setText(it.getValue<Cafe>()?.cafeSubName.toString())
            cafe_location.setText(it.getValue<Cafe>()?.location.toString())
            cafe_call.setText(it.getValue<Cafe>()?.call.toString())
            cafe_runtime.setText(it.getValue<Cafe>()?.runtime.toString())
            cafe_facility.setText(it.getValue<Cafe>()?.facility.toString())
            cafe_tumbler.setText(it.getValue<Cafe>()?.rentalTumbler.toString())
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }


        binding.SettingCafeViewSaveButtonBtn.setOnClickListener {
            FirebaseDB().writeCafeSetting(
                Firebase.auth.currentUser?.uid.toString(),
                cafe_name.text.toString(),
                cafe_subname.text.toString(),
                cafe_location.text.toString(),
                cafe_call.text.toString(),
                cafe_runtime.text.toString(),
                cafe_facility.text.toString(),
                cafe_tumbler.text.toString())
        }
    }
}