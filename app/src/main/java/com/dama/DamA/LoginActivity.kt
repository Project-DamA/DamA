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

        //회원가입 시
        binding.LoginViewJoinTv.setOnClickListener {
            startActivity(Intent(this, UserJoinActivity::class.java))

        }

        //로그인 시
        binding.LoginViewLoginBtn.setOnClickListener {
            startActivity((Intent(this, OwnerMainActivity::class.java)))
        }

    }
}