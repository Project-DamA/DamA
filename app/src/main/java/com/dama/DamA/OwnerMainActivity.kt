package com.dama.DamA


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dama.DamA.databinding.ActivityOwnerMainBinding


class OwnerMainActivity : AppCompatActivity() {
    
    lateinit var binding : ActivityOwnerMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //더보기 버튼
        binding.OwnerMainViewMoreIb.setOnClickListener {
            startActivity(Intent(this, OwnerMenuActivity::class.java))
        }
        //내 카페 설정
        binding.OwnerMainViewMycafeCv.setOnClickListener {
            startActivity(Intent(this,SettingCafeActivity::class.java))
        }


        binding.OwnerMainViewQrcodeIb.setOnClickListener{
            startActivity(Intent(this,OwnerPermitServiceActivity::class.java))
        }

        // cafeadpater와 연결
        val cafeAdapter = MyCafeAdapterClass(this)
        binding.OwnerMainViewMycafeVp.adapter=cafeAdapter

        // indicator 생성
        val indicator = binding.OwnerMainViewMycafeDotsindicatorDi
        indicator.setViewPager2(binding.OwnerMainViewMycafeVp)

    }
}