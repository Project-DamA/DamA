package com.dama.DamA


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dama.DamA.databinding.ActivityOwnerMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class OwnerMainActivity : AppCompatActivity() {
    lateinit var userCardRentalAdapter: UserCardRentalAdapter
    private lateinit var rentalUserList: ArrayList<User>
    private lateinit var expiryUserList: ArrayList<User>
    lateinit var binding : ActivityOwnerMainBinding
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rentalUserList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rentalUserList.setHasFixedSize(true)



        rentalUserList = arrayListOf()
        expiryUserList = arrayListOf()
        getUsersData()








        //더보기 버튼
        binding.OwnerMainViewMoreIb.setOnClickListener {
            startActivity(Intent(this, OwnerMenuActivity::class.java))
        }
        //내 카페 설정
        binding.OwnerMainViewMycafeCv.setOnClickListener {
            startActivity(Intent(this,SettingCafeActivity::class.java))
        }


        binding.OwnerMainViewQrcodeIb.setOnClickListener{
            startActivity(Intent(this,OwnerPermitServiceActivity::class.java))
        }

        // cafeadpater와 연결
        val cafeAdapter = MyCafeAdapterClass(this)
        binding.OwnerMainViewMycafeVp.adapter=cafeAdapter

        // indicator 생성
        val indicator = binding.OwnerMainViewMycafeDotsindicatorDi
        indicator.setViewPager2(binding.OwnerMainViewMycafeVp)

    }
    private fun getUsersData() {
        val uid=Firebase.auth.currentUser!!.uid
        dbref = FirebaseDatabase.getInstance().getReference("cafe").child(uid).child("rentalUsers")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userDBRef = FirebaseDatabase.getInstance().getReference("users").get()
                    userDBRef.addOnSuccessListener {
                        for (userSnapshot in snapshot.children) {
                            val user= it.child(userSnapshot.value.toString()).getValue<User>()
                            rentalUserList.add(user!!)


                        }
                        userCardRentalAdapter = UserCardRentalAdapter(rentalUserList)
                        binding.rentalUserList.adapter = userCardRentalAdapter

                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}