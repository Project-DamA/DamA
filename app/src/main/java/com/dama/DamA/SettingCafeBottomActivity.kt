package com.dama.DamA


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dama.DamA.databinding.ActivitySettingCafeBottomBinding


class SettingCafeBottomActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingCafeBottomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingCafeBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.SettingCafeViewBottomButtonBtn.setOnClickListener {
            var cafe_name = binding.SettingCafeViewBottomNameEt.text
            var cafe_subname = binding.SettingCafeViewBottomSubnameEt.text
            var cafe_location = binding.SettingCafeViewBottomLocationEv.text
            var cafe_runtime = binding.SettingCafeViewBottomTimeEv.text
            var cafe_call = binding.SettingCafeViewBottomPhoneEv.text
            var cafe_facility = binding.SettingCafeViewBottomFacilityEv.text


            }

    }





}
