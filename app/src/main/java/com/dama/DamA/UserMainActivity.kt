package com.dama.DamA


import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.ActivityUserMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList


class UserMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserMainBinding
    private lateinit var arrayList: ArrayList<Cafe>
    private lateinit var dbref: DatabaseReference
    private lateinit var cafeImageList:ArrayList<Uri>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        arrayList = arrayListOf()
        getCafeData()
        getUserData()
        val rentalTimeString = intent.getStringExtra("userRentalTime")
        if (rentalTimeString == null) {
            binding.UserMainViewViewpagerVp.visibility = View.INVISIBLE
        }

//        val rentalTimeString = intent.getStringExtra("userRentalTime")
//        if (rentalTimeString != null) {
//            val userRentalTime =
//                LocalDateTime.parse(rentalTimeString, DateTimeFormatter.ISO_DATE_TIME).plusDays(7)
//
//            val current = LocalDateTime.now()
//            val duration = Duration.between(userRentalTime, current).seconds
//            binding.UserMainViewReturnTimeTxt.setText("${toString()} 남았습니다.")
//
//        }


        //더보기 버튼
        binding.UserMainViewMoreIb.setOnClickListener {
            startActivity(Intent(this, UserMenuActivity::class.java))
        }

        //카페 정보
        binding.UserMainViewModifyBtn.setOnClickListener {
            var i = Intent(this, DetailCafeActivity::class.java)
            i.putExtra("cafeImageList",cafeImageList)
            startActivity(i)
        }
        getImagesViewpager("Uoro2PfTmdUD41x7lVr0Ba0Ocp93")

    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("users")
            .child(Firebase.auth.currentUser!!.uid)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val user = snapshot.getValue<User>()
                if (user!!.rentalTime != null) {
                    binding.UserMainViewNameTv
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getCafeData() {
        dbref = FirebaseDatabase.getInstance().getReference("cafe")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (cafeSnapshot in snapshot.children) {

                    val cafe = cafeSnapshot.getValue<Cafe>()
                    arrayList.add(cafe!!)


                }
//                cafeListAdapter = CafeListAdapter(this@UserMainActivity, arrayList)
//                binding.CafeList.adapter = cafeListAdapter
                binding.CafeList.adapter = CafeListAdapter(this@UserMainActivity,arrayList)
                // ViewPager의 Paging 방향은 Horizontal
                binding.CafeList.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

    fun gotoDetail(uid: String) {
        val i = Intent(this@UserMainActivity, DetailCafeActivity::class.java)
        i.putExtra("ownerUid", uid)
        startActivity(i)
    }

    private fun getImagesViewpager(uid:String) {
        Firebase.storage.reference.child("cafe_images").child(uid).listAll().addOnSuccessListener { itemsList ->
            itemsList.items.forEach { item ->
                item.downloadUrl.addOnSuccessListener { cafeImageList.add(it)
                    binding.UserMainViewViewpagerVp.adapter=ImageListAdapter(cafeImageList,this)
                    binding.UserMainViewViewpagerVp.orientation=ViewPager2.ORIENTATION_HORIZONTAL

                    // indicator 생성
                    val indicator = binding.UserMainViewIndicatorDi
                    indicator.setViewPager2(binding.UserMainViewViewpagerVp)
                }
            }

        }
    }
}