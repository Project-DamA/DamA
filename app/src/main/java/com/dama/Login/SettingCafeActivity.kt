package com.dama.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.dama.Login.databinding.ActivitySettingCafeBinding
import com.example.viewpager2.ViewPagerAdapterClass


class SettingCafeActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: ActivitySettingCafeBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivitySettingCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.SettingCafeViewToolbarTb).apply {

            title="내카페"
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewPager2 = findViewById(R.id.SettingCafeView_viewPager_vp)

        viewPager2.adapter = ViewPagerAdapterClass(this)

        val indicator = binding.SettingCafeViewIndicatorDi
        indicator.setViewPager2(binding.SettingCafeViewViewPagerVp)
    }
}