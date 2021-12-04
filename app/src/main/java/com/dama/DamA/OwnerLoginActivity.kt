package com.dama.DamA

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.dama.DamA.databinding.ActivityOwnerLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class OwnerLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityOwnerLoginBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    var email = ""
    var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOwnerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("잠시만 기다려주십시오.")
        progressDialog.setMessage("로그인 중...")
        progressDialog.setCanceledOnTouchOutside(false)

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        checkOwner()

        //유저용 로그인 전환 시
        binding.OwnerLoginViewUserBtn.setOnClickListener {
            onBackPressed()
        }


        //회원가입 시
        binding.OwnerLoginViewJoinTv.setOnClickListener {
            startActivity(Intent(this, UserJoinActivity::class.java))
        }



        auth = Firebase.auth
        //로그인 시
        binding.OwnerLoginViewLoginBtn.setOnClickListener {
            validateData()
        }

        //비밀번호 찾기
        binding.OwnerLoginViewSerachPasswordTv.setOnClickListener {

        }

    }
    private fun checkOwner() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            database.child("owners").get().addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    var i:Intent=Intent()
                    val uid=firebaseUser.uid
                    i = if (it.result?.hasChild(uid) == true) {
                        Intent(this, OwnerMainActivity::class.java)

                    } else{
                        Intent(this, UserMainActivity::class.java)
                    }
                    startActivity(i)
                    finish()
                }
            }
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
                    database.child("owners").get().addOnCompleteListener(this) {
                        val uid=auth.currentUser?.uid.toString()
                        if (it.isSuccessful) {
                            if (it.result?.hasChild(uid)==true) {
                                Toast.makeText(
                                    baseContext, "로그인 성공.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val i = Intent(this, OwnerMainActivity::class.java)
                                startActivity(i)
                                finish()
                            } else {
                                auth.signOut()
                                Toast.makeText(
                                    baseContext, "사장님 계정이 아닙니다.",
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
        email = binding.OwnerLoginViewEmailTextBoxEditTxt.text.toString().trim()
        password = binding.OwnerLoginViewPasswordTextBoxEditTxt.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.OwnerLoginViewEmailTextBoxEditTxt.error = "지정된 형식이 아닙니다."
        }

        when {
            TextUtils.isEmpty(password) -> {
                binding.OwnerLoginViewPasswordTextBoxEditTxt.error = "패스워드를 입력해주세요."
            }
            password.length < 6 -> {
                binding.OwnerLoginViewPasswordTextBoxEditTxt.error = "패스워드는 6자리 이상입니다."
            }
            else -> {
                firebaseLogin()
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }
}