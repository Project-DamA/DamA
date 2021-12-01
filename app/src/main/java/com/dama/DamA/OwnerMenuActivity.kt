package com.dama.DamA

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dama.DamA.databinding.ActivityOwnerMenuBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

import java.util.*
import kotlin.collections.HashMap

class OwnerMenuActivity : AppCompatActivity(){

    private lateinit var database: DatabaseReference
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


        //이 코드는 rent 안에 test 가 key값인 항목만 지워줌 -> 추후에 child("request").child("rental").child(특정데이터)형식으로 수정 바람
        binding.OwnerMenuViewTemporaryBtn.setOnClickListener {
            var mDatabase = FirebaseDatabase.getInstance();
            var dataRef = mDatabase.getReference("rent").child("test")

            var now = System.currentTimeMillis()
            var date = Date(now)

//지우기 전에 userId 읽어서 저장해 두기
           var userid = database.child("rent").child("test").get()
//49번줄 읽어들이기 부터 해야됨
            dataRef.removeValue();  //지우는코드
            FirebaseDB().writeUserTumblerTime((date).toString())   //userDB에 빌린 시간 추가
            FirebaseDB().writeCafeRentalUsers((userid).toString())     //cafeDB에 rentalUsers 에 추가

        }
    }
}