package com.dama.DamA


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginRight
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.bumptech.glide.Glide
import com.dama.DamA.databinding.ActivityOwnerMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class OwnerMainActivity : AppCompatActivity() {
    private lateinit var rentalUserList: ArrayList<User>
    private lateinit var expiryUserList: ArrayList<User>
    private lateinit var cafeImageList: ArrayList<Uri>
    lateinit var binding: ActivityOwnerMainBinding
    private lateinit var dbref: DatabaseReference
    val uid = Firebase.auth.currentUser!!.uid
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityOwnerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        cafeImageList = arrayListOf()
        rentalUserList = arrayListOf()
        expiryUserList = arrayListOf()
        getImagesViewpager()

        getUsersData()
        getCafeInfo()
        //viewpager
        binding.OwnerMainViewCafePhotoVp.adapter = ImageListAdapter(cafeImageList, this)
        binding.OwnerMainViewCafePhotoVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.OwnerMainViewCafePhotoVp.clipToPadding = false;

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
        binding.OwnerMainViewCafePhotoVp.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }


        // indicator 생성
        val indicator = binding.OwnerMainViewCafePhotoIndicatorDi
        indicator.setViewPager2(binding.OwnerMainViewCafePhotoVp)

        binding.OwnerMainViewCafePhotoVp.offscreenPageLimit = 3


        //recyclerview
        binding.OwnerMainVIewRentalUserListRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.OwnerMainVIewRentalUserListRv.setHasFixedSize(true)
        val size = resources.getDimensionPixelSize(R.dimen.item_margin)
        val deco = SpaceDecoration(size)
        binding.OwnerMainVIewRentalUserListRv.addItemDecoration(deco)


        //내 카페 설정
        binding.OwnerMainViewMycafemodifyIb.setOnClickListener {
            val i = Intent(this, SettingCafeActivity::class.java)
            i.putExtra("addImageList", arrayListOf<Uri>())
            i.putExtra("cafeImageList", cafeImageList)
            startActivity(i)

        }

        //qr
        binding.OwnerMainViewQrcodeIb.setOnClickListener {
            startActivity(Intent(this, OwnerPermitServiceActivity::class.java))
        }

        //로그아웃
        binding.OwnerMainViewLogoutBtn.setOnClickListener {
            if (Firebase.auth.currentUser!=null)
                Firebase.auth.signOut()
            val i = Intent(this, UserLoginActivity::class.java)
            // set the new task and clear flags
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }


    }

    private fun getCafeInfo() {

        dbref = FirebaseDatabase.getInstance().getReference("cafe").child(uid)
        dbref.get().addOnSuccessListener {
            val cafe = it.getValue<Cafe>()!!
            binding.OwnerMainViewCafeNameTv.text = cafe.cafeName
            if (cafe.totalTumbler != null && cafe.rentalTumbler != null) {
                val tumbler = cafe.totalTumbler.toInt() - cafe.rentalTumbler.toInt()

                binding.OwnerMainViewCafeTumblerCountTv.text = "현재 보유중인 텀블러 : ${tumbler}개"
                binding.OwnerMainViewRentalUsersTumblerCountTv.text =
                    "현재 대여중인 텀블러 : ${cafe.rentalTumbler}개"
            }

        }


    }

    private fun getUsersData() {

        dbref = FirebaseDatabase.getInstance().getReference("cafe").child(uid).child("rentalUsers")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                rentalUserList = arrayListOf()
                if (snapshot.exists()) {
                    val userDBRef = FirebaseDatabase.getInstance().getReference("users").get()
                    userDBRef.addOnSuccessListener {
                        for (userSnapshot in snapshot.children) {
                            val user = it.child(userSnapshot.value.toString()).getValue<User>()
                            rentalUserList.add(user!!)


                        }



                        binding.OwnerMainVIewRentalUserListRv.adapter = UserCardRentalAdapter(rentalUserList)
//                        binding.rentalUserList.orientation=ViewPager2.ORIENTATION_HORIZONTAL

                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

    private fun getImagesViewpager() {
        Firebase.storage.reference.child("cafe_images").child(uid).listAll()
            .addOnSuccessListener { itemsList ->
                itemsList.items.forEach { item ->
                    item.downloadUrl.addOnSuccessListener {
                        cafeImageList.add(it)
                        binding.OwnerMainViewCafePhotoVp.adapter?.notifyDataSetChanged()
                    }

                }

            }

    }


}

