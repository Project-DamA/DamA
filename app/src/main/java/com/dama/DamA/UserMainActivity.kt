package com.dama.DamA


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.dama.DamA.databinding.ActivityUserMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class UserMainActivity : AppCompatActivity() {
    
    lateinit var binding : ActivityUserMainBinding
    private lateinit var arrayList: ArrayList<Cafe>
    private lateinit var dbref: DatabaseReference
    private lateinit var cafeListAdapter: CafeListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.CafeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.CafeList.setHasFixedSize(true)

        arrayList = arrayListOf()
        getCafeData()









        //더보기 버튼
        binding.UserMainViewMoreIb.setOnClickListener {
            startActivity(Intent(this, UserMenuActivity::class.java))
        }

        //카페 정보
        binding.UserMainViewUsingCV.setOnClickListener {
            var i=Intent(this,DetailCafeActivity::class.java)
            startActivity(i)
        }

        // cafeadpater와 연결
        val cafeAdapter = UserCafeAdapterClass(this)
        binding.UserMainViewViewpagerVp.adapter=cafeAdapter

        // indicator 생성
        val indicator = binding.UserMainViewIndicatorDi
        indicator.setViewPager2(binding.UserMainViewViewpagerVp)

    }

    private fun getCafeData() {
        dbref = FirebaseDatabase.getInstance().getReference("cafe")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                        for (cafeSnapshot in snapshot.children) {

                            val cafe= cafeSnapshot.getValue<Cafe>()
                            arrayList.add(cafe!!)


                        }
                            cafeListAdapter = CafeListAdapter(this@UserMainActivity,arrayList)
                        binding.CafeList.adapter = cafeListAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
    fun gotoDetail(uid:String){
        val i= Intent(this@UserMainActivity,DetailCafeActivity::class.java)
        i.putExtra("ownerUid",uid)
        startActivity(i)
    }
}