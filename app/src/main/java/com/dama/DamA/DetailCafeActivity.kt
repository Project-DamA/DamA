package com.dama.DamA

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.ActivityDetailCafeBinding


class DetailCafeActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: ActivityDetailCafeBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityDetailCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.DetailCafeViewToolbarTb).apply {
            title="내카페"
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewPager2 = findViewById(R.id.DetailCafeView_viewPager_vp)

        viewPager2.adapter = ViewPagerAdapterClass(this)

        val indicator = binding.DetailCafeViewIndicatorDi
        indicator.setViewPager2(binding.DetailCafeViewViewPagerVp)
    }
}