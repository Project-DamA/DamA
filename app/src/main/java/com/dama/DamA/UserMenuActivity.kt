package com.dama.DamA


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dama.DamA.databinding.ActivityUserMenuBinding


class UserMenuActivity : AppCompatActivity(){

    lateinit var binding : ActivityUserMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuPreviousMoveIB.setOnClickListener {
            finish()
        }
    }
}