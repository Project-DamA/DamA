package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dama.DamA.databinding.ActivityOwnerJoinBinding


class OwnerJoinActivity : AppCompatActivity() {
    lateinit var binding : ActivityOwnerJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityOwnerJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //사장님용 뷰 화면 to do delete
        binding.OwnerJoinViewJoinBtn.setOnClickListener {
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