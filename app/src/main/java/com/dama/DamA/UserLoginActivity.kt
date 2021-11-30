package com.dama.DamA

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.dama.DamA.databinding.ActivityUserLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class UserLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserLoginBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var progressDialog: ProgressDialog
    var email = ""
    var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("잠시만 기다려주십시오.")
        progressDialog.setMessage("로그인 중...")
        progressDialog.setCanceledOnTouchOutside(false)



        auth = FirebaseAuth.getInstance()
        checkUser()


        database = Firebase.database.reference


        //사장님 로그인 전환 시
        binding.UserLoginViewAdminBtn.setOnClickListener {
            startActivity(Intent(this, OwnerLoginActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        //회원가입 시
        binding.UserLoginViewJoinTv.setOnClickListener {
            startActivity(Intent(this, UserJoinActivity::class.java))
        }

        //로그인 시
        binding.UserLoginViewLoginBtn.setOnClickListener {
            validateData()
        } //오너계정을 유저 로그인에 하니 로그인 됨 반대도 됨


        binding.UserLoginViewSerachPasswordTv.setOnClickListener {

        }
    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, UserMainActivity::class.java))
            finish()
        }
    }


    private fun firebaseLogin() {
        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    database.child("users").get().addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            if (it.result?.hasChild(auth.currentUser?.uid.toString())==true) {
                                Toast.makeText(
                                    baseContext, "로그인 성공.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val i = Intent(this, UserMainActivity::class.java)
                                startActivity(i)
                                finish()
                            } else {
                                Toast.makeText(
                                    baseContext, "유저 계정이 아닙니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithEmail:failure", task.exception)
                            progressDialog.dismiss()
                            Toast.makeText(
                                baseContext, "로그인 실패. 잠시 후 다시 시도해 주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }

            }

    }

    private fun validateData() {
        email = binding.UserLoginViewEmailTextBoxEditTxt.text.toString().trim()
        password = binding.UserLoginViewPasswordTextBoxEditTxt.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.UserLoginViewEmailTextBoxEditTxt.error = "지정된 형식이 아닙니다."
        }

        when {
            TextUtils.isEmpty(password) -> {
                binding.UserLoginViewPasswordTextBoxEditTxt.error = "패스워드를 입력해주세요."
            }
            password.length < 6 -> {
                binding.UserLoginViewPasswordTextBoxEditTxt.error = "패스워드는 6자리 이상입니다."
            }
            else -> {
                firebaseLogin()
            }
        }

    }


}
