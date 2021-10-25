package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dama.DamA.databinding.ActivityUserJoinBinding


class UserJoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //사장님 화면 뷰 to do delete
        binding.UserJoinViewJoinBtn.setOnClickListener {
            startActivity(Intent(this,UserMainActivity::class.java))
        }


        //사장님용 전환 버튼
        binding.UserJoinViewAdminBtn.setOnClickListener {
            startActivity(Intent(this,OwnerJoinActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }
    }
}
