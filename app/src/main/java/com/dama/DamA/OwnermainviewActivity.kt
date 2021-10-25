package com.dama.DamA


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dama.DamA.databinding.ActivityOwnerMainBinding


class OwnermainviewActivity : AppCompatActivity() {
    
    lateinit var binding : ActivityOwnerMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.OwnerMainViewMoreIb.setOnClickListener {
            startActivity(Intent(this, OwnermenuviewActivity::class.java))
        }

        // cafeadpater와 연결
        val cafeAdapter = MyCafeAdapterClass(this)
        binding.OwnerMainViewMycafeVp.adapter=cafeAdapter

        // indicator 생성
        val indicator = binding.OwnerMainViewMycafeDotsindicatorDi
        indicator.setViewPager2(binding.OwnerMainViewMycafeVp)

    }
}