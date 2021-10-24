package com.dama.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.dama.Login.databinding.ActivityOwnerJoinBinding


class OwnerJoinActivity : AppCompatActivity() {
    lateinit var binding : ActivityOwnerJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOwnerJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.OwnerJoinViewUserBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }
}