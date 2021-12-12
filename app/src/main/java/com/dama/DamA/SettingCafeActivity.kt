package com.dama.DamA

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.dama.DamA.databinding.ActivitySettingCafeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SettingCafeActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    private lateinit var binding: ActivitySettingCafeBinding
    private lateinit var database: DatabaseReference
    private lateinit var cafeImageList: ArrayList<Uri>
    private lateinit var addImageList: ArrayList<Uri>

    val uid=Firebase.auth.currentUser!!.uid
    private var launcher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            if (it != null) {
                for( i in it) {
                    cafeImageList.add(i)
                    addImageList.add(i)
                }
                intent.putExtra("cafeImageList",cafeImageList)
                intent.putExtra("addImageList",addImageList)
            }
            getImagesViewpager()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        database = Firebase.database.reference
        storage = Firebase.storage

        super.onCreate(savedInstanceState)
        binding = ActivitySettingCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

        }

        cafeImageList = intent.getParcelableArrayListExtra<Uri>("cafeImageList") as ArrayList<Uri>
        addImageList= intent.getParcelableArrayListExtra<Uri>("addImageList") as ArrayList<Uri>

        getImagesViewpager()



        var cafe_name = binding.SettingCafeViewNameEt
        var cafe_subname = binding.SettingCafeViewSubNameEt
        var cafe_location = binding.SettingCafeViewLocationEv
        var cafe_call = binding.SettingCafeViewPhoneEv
        var cafe_runtime = binding.SettingCafeViewTimeEv
        var cafe_facility = binding.SettingCafeViewFacilityEv
        var cafe_tumbler = binding.SettingCafeViewRentaltumblerEv




        database.child("cafe").child(uid).get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                if (it.exists()) {
                    var cafeData = it.getValue<Cafe>()
                    setSupportActionBar(binding.SettingCafeViewToolbarTb).apply {
                        title = cafeData?.cafeName.toString()
                    }
                    cafe_name.setText(cafeData?.cafeName.toString())
                    cafe_subname.setText(cafeData?.cafeSubName.toString())
                    cafe_location.setText(cafeData?.location.toString())
                    cafe_call.setText(cafeData?.call.toString())
                    cafe_runtime.setText(cafeData?.runtime.toString())
                    cafe_facility.setText(cafeData?.facility.toString())
                    cafe_tumbler.setText(cafeData?.totalTumbler.toString())
                }
            }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }


        binding.SettingCafeViewSaveButtonBtn.setOnClickListener {
            val cafe: Cafe = Cafe(
                uid,
                cafe_name.text.toString(),
                cafe_subname.text.toString(),
                cafe_location.text.toString(),
                cafe_call.text.toString(),
                cafe_runtime.text.toString(),
                cafe_facility.text.toString(),
                cafe_tumbler.text.toString()
            )
            FirebaseDB().writeCafeSetting(cafe)
            repeat(addImageList.size) {
                uploadImageTOFirebase(addImageList[it],it.toString())
            }
            addImageList= arrayListOf<Uri>()

        }


        binding.imageView.setOnClickListener {
            initGallery()
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )

        }

    }

    private fun getImagesViewpager() {
        if(cafeImageList.isEmpty()){
            binding.SettingCAfeViewNoImageTv.visibility= TextView.VISIBLE

        }
        else {
            binding.SettingCafeViewViewPagerVp.adapter = ImageListAdapter(cafeImageList, this)
            binding.SettingCafeViewViewPagerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            // indicator 생성
            val indicator = binding.SettingCafeViewIndicatorDi
            indicator.setViewPager2(binding.SettingCafeViewViewPagerVp)
        }
    }
    private fun uploadImageTOFirebase(uri: Uri,filesubName:String) {
        var storage: FirebaseStorage? = FirebaseStorage.getInstance()                    //FirebaseStorage 인스턴스 생성
        //파일 이름 생성.
        var fileName = "Cafe_IMAGE_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}_${filesubName}_.png"
        //파일 업로드, 다운로드, 삭제, 메타데이터 가져오기 또는 업데이트를 하기 위해 참조를 생성.
        //참조는 클라우드 파일을 가리키는 포인터라고 할 수 있음.
        var imagesRef = storage!!.reference.child("cafe_images").child(uid).child(fileName)    //기본 참조 위치/images/${fileName}
        //이미지 파일 업로드
        imagesRef.putFile(uri).addOnSuccessListener {
            Toast.makeText(this, "upload_success", Toast.LENGTH_SHORT) .show()
        }.addOnFailureListener {
            println(it)
            Toast.makeText(this, "upload_fail", Toast.LENGTH_SHORT).show()
        }
    }

    fun initGallery() {
        //앱이 갤러리에 접근햐는 것을 허용했을 경우
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch("image/*")  //갤러리로 이동하는 런처 실행.
        } else {    //앱이 갤러리에 접근햐는 것을 허용하지 않았을 경우
            Toast.makeText(
                this,
                "갤러리 접근 권한이 거부돼 있습니다. 설정에서 접근을 허용해 주세요.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}