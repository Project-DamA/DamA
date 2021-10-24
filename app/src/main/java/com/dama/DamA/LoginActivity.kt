package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dama.DamA.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.LoginViewJoinTv.setOnClickListener {
            startActivity(Intent(this, UserJoinViewActivity::class.java))

        }
        binding.LoginViewLoginBtn.setOnClickListener {
            startActivity((Intent(this, SettingCafeActivity::class.java)))
        }

    }
}