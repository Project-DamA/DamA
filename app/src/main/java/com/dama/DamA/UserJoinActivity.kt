package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dama.DamA.databinding.ActivityUserJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class UserJoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserJoinBinding

    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //사장님용 전환 버튼
        binding.UserJoinViewAdminBtn.setOnClickListener {
            startActivity(Intent(this, OwnerJoinActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        auth = Firebase.auth
        //회원가입 버튼
        binding.UserJoinViewJoinBtn.setOnClickListener {
            var email = binding.UserJoinViewEmailTextBoxEditTxt.text.toString()
            var password = binding.UserJoinViewPasswordTextBoxEditTxt.text.toString()
            var passwordcheck = binding.UserJoinViewPasswordCheckTextBoxEditTxt.text.toString()
            var username=binding.UserJoinViewNameTextBoxEditTxt.text.toString()
            var age = binding.UserJoinViewAgeTextBoxEditTxt.text.toString()
            var phoneNumber = binding.UserJoinViewPhoneTextBoxEditTxt.text.toString()
            if (password != passwordcheck) {
                val builder = AlertDialog.Builder(this)

                builder.setTitle("회원가입 오류").setMessage("비밀번호를 확인하세요")

                val alertDialog = builder.create()

                alertDialog.show()
            }

            if (password.length < 6 ) {
                val builder = AlertDialog.Builder(this)

                builder.setTitle("회원가입 오류").setMessage("비밀번호는 6자리 이상입니다")

                val alertDialog = builder.create()

                alertDialog.show()
            }

            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this, "계정 생성 완료.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // 가입창 종료
                        FirebaseDB().writeNewUser(Firebase.auth.currentUser?.uid.toString(),username,email,phoneNumber)
                        startActivity(Intent(this,UserMainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }



//
        }//비밀번호 확인 이랑 안맞을 경우, 비밀번호 6자리이상

    }

}