package com.dama.DamA

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dama.DamA.databinding.ActivityTestImageBinding


class TestImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestImageBinding
    val manager = supportFragmentManager

    private fun changeFragment(fragment: GalleryFragment){
        val transaction = manager.beginTransaction()
        transaction.add(R.id.fragment_holder, fragment)
//        transaction.addToBackStack(null)
        transaction.commit()
    }

    private var launcher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { it ->
            it?.let { it1 -> GalleryFragment(it1) }?.let { it2 -> changeFragment(it2) }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigationBar() //네이게이션 바의 각 메뉴 탭을 눌렀을 때 화면이 전환되도록 하는 함수.

        //앱에서 앨범에 접근을 허용할지 선택하는 메시지, 한 번 허용하면 앱이 설치돼 있는 동안 다시 뜨지 않음.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1
        )
    }

    fun initNavigationBar() {
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


}