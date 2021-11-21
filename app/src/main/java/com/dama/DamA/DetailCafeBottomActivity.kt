package com.dama.DamA

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dama.DamA.databinding.ActivityDetailCafeBottomBinding

class DetailCafeBottomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCafeBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailCafeBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}