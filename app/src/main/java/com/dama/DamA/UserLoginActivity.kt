package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.dama.DamA.databinding.ActivityUserLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //사장님 로그인 전환 시
        binding.UserLoginViewAdminBtn.setOnClickListener {
            startActivity(Intent(this,OwnerLoginActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }

        //회원가입 시
            binding.UserLoginViewJoinTv.setOnClickListener {
            startActivity(Intent(this, UserJoinActivity::class.java))
        }

        //로그인 시
        binding.UserLoginViewLoginBtn.setOnClickListener {
            var email = binding.LoginViewEmailTextBoxEditTxt.text.toString()
            var password = binding.UserLoginViewPasswordTextBoxEditTxt.text.toString()

        }

    }
}