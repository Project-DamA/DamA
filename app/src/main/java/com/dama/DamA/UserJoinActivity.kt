package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.dama.DamA.databinding.ActivityUserJoinBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class UserJoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //유저 화면 뷰 to do delete
        binding.UserJoinViewJoinBtn.setOnClickListener {
            var email = binding.UserJoinViewEmailTextBoxEditTxt.text.toString()
            var password = binding.UserJoinViewPasswordTextBoxEditTxt.text.toString()
            var username = binding.UserJoinViewNameTextBoxEditTxt.text.toString()
            var age = binding.UserJoinViewAgeTextBoxEditTxt.text.toString()
           startActivity(Intent(this,UserMainActivity::class.java))
        }


        //사장님용 전환 버튼
        binding.UserJoinViewAdminBtn.setOnClickListener {
            startActivity(Intent(this,OwnerJoinActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }
    }
}
