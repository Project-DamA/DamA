package com.dama.DamA


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dama.DamA.databinding.ActivityUserMainBinding


class UserMainActivity : AppCompatActivity() {
    
    lateinit var binding : ActivityUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //더보기 버튼
        binding.UserMainViewMoreIb.setOnClickListener {
            startActivity(Intent(this, UserMenuActivity::class.java))
        }

        // cafeadpater와 연결
        val cafeAdapter = UserCafeAdapterClass(this)
        binding.UserMainViewViewpagerVp.adapter=cafeAdapter

        // indicator 생성
        val indicator = binding.UserMainViewIndicatorDi
        indicator.setViewPager2(binding.UserMainViewViewpagerVp)

    }
}