package com.dama.DamA


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dama.DamA.databinding.ActivityUserMenuBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class UserMenuActivity : AppCompatActivity(){

    lateinit var binding : ActivityUserMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.UserMenuViewCloseIb.setOnClickListener {
            finish()
        }

        binding.UserMenuViewLogoutCv.setOnClickListener {
            if (Firebase.auth.currentUser!=null)
                Firebase.auth.signOut()
            val i = Intent(this, UserLoginActivity::class.java)
            // set the new task and clear flags
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(i)
        }
    }
}