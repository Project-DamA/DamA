package com.dama.DamA


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    val uid=Firebase.auth.currentUser!!.uid
    private val manager = supportFragmentManager
    val transaction = manager.beginTransaction()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityOwnerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.rentalUserList.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.rentalUserList.setHasFixedSize(true)


        rentalUserList = arrayListOf()
        expiryUserList = arrayListOf()

        transaction.add(R.id.rentalUserList,UserCardVIewPagerFragment(rentalUserList))

        getUsersData()

        getCafeInfo()






        //더보기 버튼
        binding.OwnerMainViewMoreIb.setOnClickListener {
            startActivity(Intent(this, OwnerMenuActivity::class.java))
        }
        //내 카페 설정
        binding.OwnerMainViewMycafemodifyIb.setOnClickListener {
            startActivity(Intent(this,SettingCafeActivity::class.java))

        }

        //qr
        binding.OwnerMainViewQrcodeIb.setOnClickListener{
            startActivity(Intent(this,OwnerPermitServiceActivity::class.java))
        }

        // cafeadpater와 연결
        val cafeAdapter = MyCafeAdapterClass(this)
        binding.OwnerMainViewCafePhotoVp.adapter=cafeAdapter

        // indicator 생성
        val indicator = binding.OwnerMainViewCafePhotoIndicatorDi
        indicator.setViewPager2(binding.OwnerMainViewCafePhotoVp)

    }
    private fun getCafeInfo(){

        dbref = FirebaseDatabase.getInstance().getReference("cafe").child(uid)
        dbref.get().addOnSuccessListener {
            val cafe= it.getValue<Cafe>()!!
            binding.OwnerMainViewCafeNameTv.text=cafe.cafeName
            if(cafe.totalTumbler!=null&&cafe.rentalTumbler!=null){
                val tumbler=cafe.totalTumbler!!.toInt()-cafe.rentalTumbler!!.toInt()

                binding.OwnerMainViewCafeTumblerCountTv.setText("현재 ${tumbler}개의 텀블러를 대여중입니다.")
                binding.OwnerMainViewRentalUsersTumblerCountTv.setText("현재 ${cafe.rentalTumbler}개의 텀블러가 대여중입니다.")
            }

        }


    }

    private fun getUsersData() {

        dbref = FirebaseDatabase.getInstance().getReference("cafe").child(uid).child("rentalUsers")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                rentalUserList= arrayListOf()
                if (snapshot.exists()) {
                    val userDBRef = FirebaseDatabase.getInstance().getReference("users").get()
                    userDBRef.addOnSuccessListener {
                        for (userSnapshot in snapshot.children) {
                            val user= it.child(userSnapshot.value.toString()).getValue<User>()
                            rentalUserList.add(user!!)


                        }

                        ///////

                        transaction.replace(R.id.rentalUserList, UserCardVIewPagerFragment(rentalUserList))
//        transaction.addToBackStack(null)
                        transaction.commit()

                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}