package com.dama.DamA

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dama.DamA.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth



class ResetPasswordActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth =FirebaseAuth.getInstance()

        var useremail = binding.ResetPasswordViewEmailTv


        fun findPassword(){
            FirebaseAuth.getInstance().sendPasswordResetEmail(useremail.text.toString()).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "비밀번호 변경 메일을 전송했습니다", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }


        binding.ResetPasswordViewBtn.setOnClickListener {
            findPassword()


        }

    }
}




