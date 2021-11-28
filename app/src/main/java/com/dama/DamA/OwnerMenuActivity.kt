package com.dama.DamA

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dama.DamA.databinding.ActivityOwnerMenuBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OwnerMenuActivity : AppCompatActivity(){

    lateinit var binding : ActivityOwnerMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.OwnerMenuViewCloseIb.setOnClickListener {
            finish()
        }

        binding.OwnerMenuViewLogoutCv.setOnClickListener {
            if (Firebase.auth.currentUser!=null)
                Firebase.auth.signOut()
                val i = Intent(this, UserLoginActivity::class.java)
    // set the new task and clear flags
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
        }
    }
}