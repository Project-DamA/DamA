package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.dama.DamA.databinding.ActivityOwnerJoinBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class OwnerJoinActivity : AppCompatActivity() {
    lateinit var binding : ActivityOwnerJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding= ActivityOwnerJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)




        //사장님용 뷰 화면 to do delete
        binding.OwnerJoinViewJoinBtn.setOnClickListener {

            var email = binding.OwnerJoinViewEmailTextBoxEditTxt.text.toString()
            var password = binding.OwnerJoinViewPasswordTextBoxEditTxt.text.toString()
            var username = binding.OwnerJoinViewNameTextBoxEditTxt.text.toString()
            var age = binding.OwnerJoinViewAgeTextBoxEditTxt.text.toString()

            startActivity(Intent(this,OwnerMainActivity::class.java))
        }

        //회원용 버튼
        binding.OwnerJoinViewUserBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }
}