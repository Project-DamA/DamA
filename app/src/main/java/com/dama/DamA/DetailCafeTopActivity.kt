package com.dama.DamA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.ActivityDetailCafeTopBinding


class DetailCafeTopActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: ActivityDetailCafeTopBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityDetailCafeTopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.DetailCafeViewTopToolbarTb).apply {

            title="내카페"
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewPager2 = findViewById(R.id.DetailCafeViewTop_viewPager_vp)

        viewPager2.adapter = ViewPagerAdapterClass(this)

        val indicator = binding.DetailCafeViewTopIndicatorDi
        indicator.setViewPager2(binding.DetailCafeViewTopViewPagerVp)
    }
}