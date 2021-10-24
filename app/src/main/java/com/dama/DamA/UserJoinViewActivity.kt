package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dama.DamA.databinding.ActivityUserJoinBinding


class UserJoinViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.UserJoinViewAdminBtn.setOnClickListener {
            startActivity(Intent(this,OwnerJoinActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }
    }
}
