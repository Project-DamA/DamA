package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dama.DamA.databinding.ActivityUserLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class UserLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //사장님 로그인 전환 시
        binding.UserLoginViewAdminBtn.setOnClickListener {
            startActivity(Intent(this,OwnerLoginActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }

        //회원가입 시
        binding.UserLoginViewJoinTv.setOnClickListener {
            startActivity(Intent(this, UserJoinActivity::class.java))
        }
        auth = Firebase.auth
        //로그인 시
        binding.UserLoginViewLoginBtn.setOnClickListener {
            var email = binding.LoginViewEmailTextBoxEditTxt.text.toString()
            var password = binding.UserLoginViewPasswordTextBoxEditTxt.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        val user = auth.currentUser
                        print("Login")
                        Toast.makeText(baseContext, "Authentication Success.",
                            Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,UserMainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } //오너계정을 유저 로그인에 하니 로그인 됨 반대도 됨


        binding.UserLoginViewSerachPasswordTv.setOnClickListener{

        }
    }


}