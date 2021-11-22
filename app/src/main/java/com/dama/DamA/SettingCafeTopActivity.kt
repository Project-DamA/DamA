package com.dama.DamA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.ActivitySettingCafeTopBinding


class SettingCafeTopActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: ActivitySettingCafeTopBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivitySettingCafeTopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.SettingCafeViewTopToolbarTb).apply {

            title="내카페"
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewPager2 = findViewById(R.id.SettingCafeViewTop_viewPager_vp)

        viewPager2.adapter = ViewPagerAdapterClass(this)

        val indicator = binding.SettingCafeViewTopIndicatorDi
        indicator.setViewPager2(binding.SettingCafeViewTopViewPagerVp)
    }
}