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

//        if (Firebase.auth.currentUser!=null){
//            if (FirebaseDB().userOrOwner()=="User") {
//                print(message = "User")
////                startActivity(Intent(this, UserMainActivity::class.java))
//            }
//            else{
////                startActivity(Intent(this, OwnerMainActivity::class.java))
//            }
//            finish()
//        }






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
            val email = binding.LoginViewEmailTextBoxEditTxt.text.toString()
            val password = binding.UserLoginViewPasswordTextBoxEditTxt.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        if (true) {
                            Toast.makeText(
                                baseContext, "로그인 성공.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val i = Intent(this, UserMainActivity::class.java)
                            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(i)
                        }
                        else{
                            Toast.makeText(
                                baseContext, "유저 계정이 아닙니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "로그인 실패. 잠시 후 다시 시도해 주세요.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } //오너계정을 유저 로그인에 하니 로그인 됨 반대도 됨


        binding.UserLoginViewSerachPasswordTv.setOnClickListener{

        }
    }
}