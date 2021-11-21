package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.dama.DamA.databinding.ActivityOwnerLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OwnerLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityOwnerLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOwnerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //유저용 로그인 전환 시
        binding.OwnerLoginViewUserBtn.setOnClickListener {
            onBackPressed()
        }


        //회원가입 시
            binding.OwnerLoginViewJoinTv.setOnClickListener {
            startActivity(Intent(this, UserJoinActivity::class.java))
        }

        //로그인 시
        binding.OwnerLoginViewLoginBtn.setOnClickListener {
            var email = binding.OwnerLoginViewEmailTextBoxEditTxt.text.toString()
            var password = binding.OwnerLoginViewPasswordTextBoxEditTxt.text.toString()

            startActivity(Intent(this@OwnerLoginActivity, OwnerMainActivity::class.java))

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }
}