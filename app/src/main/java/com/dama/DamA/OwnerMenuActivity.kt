package com.dama.DamA

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dama.DamA.databinding.ActivityOwnerMenuBinding

class OwnerMenuActivity : AppCompatActivity(){

    lateinit var binding : ActivityOwnerMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.OwnerMenuViewPreviousmoveIb.setOnClickListener {
            finish()
        }
    }
}