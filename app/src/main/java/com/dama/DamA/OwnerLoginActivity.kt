package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dama.DamA.databinding.ActivityOwnerLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class OwnerLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityOwnerLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOwnerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
            var email = binding.OwnerLoginViewEmailTextBoxEditTxt.text.toString()
            var password = binding.OwnerLoginViewPasswordTextBoxEditTxt.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        val user = auth.currentUser
                        print("Login")
                        Toast.makeText(baseContext, "Authentication Success.",
                            Toast.LENGTH_SHORT).show()
                        var i = Intent(this,OwnerMainActivity::class.java)
                        i.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(i)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }
}