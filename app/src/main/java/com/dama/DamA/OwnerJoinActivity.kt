package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dama.DamA.databinding.ActivityOwnerJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class OwnerJoinActivity : AppCompatActivity() {
    lateinit var binding : ActivityOwnerJoinBinding

    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding= ActivityOwnerJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)




        //사장님용 뷰 화면 to do delete
        binding.OwnerJoinViewJoinBtn.setOnClickListener {

            var email = binding.OwnerJoinViewEmailTextBoxEditTxt.text.toString()
            var password = binding.OwnerJoinViewPasswordTextBoxEditTxt.text.toString()
            var username = binding.OwnerJoinViewNameTextBoxEditTxt.text.toString()
            var age = binding.OwnerJoinViewAgeTextBoxEditTxt.text.toString()

            startActivity(Intent(this,OwnerMainActivity::class.java))
        }

        //회원용 버튼
        binding.OwnerJoinViewUserBtn.setOnClickListener {
            onBackPressed()
        }

        auth = Firebase.auth
        //회원가입 버튼
        binding.OwnerJoinViewJoinBtn.setOnClickListener {
            var email = binding.OwnerJoinViewEmailTextBoxEditTxt.text.toString()
            var password = binding.OwnerJoinViewPasswordTextBoxEditTxt.text.toString()
            var passwordcheck = binding.OwnerJoinViewPasswordCheckTextBoxEditTxt.text.toString()
            var ownername=binding.OwnerJoinViewNameTextBoxEditTxt.text.toString()
            var age = binding.OwnerJoinViewAgeTextBoxEditTxt.text.toString()
            var phoneNumber = binding.OwnerJoinViewPhoneTextBoxEditTxt.text.toString()
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
                        val owner=Owner(Firebase.auth.currentUser?.uid.toString(),ownername,email,phoneNumber)
                        FirebaseDB().writeNewOwner(owner)

                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }




        }//비밀번호 확인 이랑 안맞을 경우, 비밀번호 6자리이상
    }



    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }
}
//test