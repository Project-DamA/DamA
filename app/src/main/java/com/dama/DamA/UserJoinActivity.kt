package com.dama.DamA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.dama.DamA.databinding.ActivityUserJoinBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class UserJoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserJoinBinding
    var userjoin:Join? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrofit 서버 연결
        var retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var joinService: JoinService = retrofit.create(JoinService::class.java)


        //유저 화면 뷰 to do delete
        binding.UserJoinViewJoinBtn.setOnClickListener {
            var email = binding.UserJoinViewEmailTextBoxEditTxt.text.toString()
            var password = binding.UserJoinViewPasswordTextBoxEditTxt.text.toString()
            var username = binding.UserJoinViewNameTextBoxEditTxt.text.toString()
            var age = binding.UserJoinViewAgeTextBoxEditTxt.text.toString()
            joinService.requestLogin(email, password, username, age).enqueue(object: Callback<Join> {
                override fun onFailure(call: Call<Join>, t: Throwable) {
                    Log.e("LOGIN", t.message.toString())
                    var dialog = AlertDialog.Builder(this@UserJoinActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()

                }
                override fun onResponse(call: Call<Join>, response: Response<Join>) {
                    userjoin = response.body()
                    Log.d("LOGIN","msg : "+userjoin?.msg)
                    Log.d("LOGIN","code : "+userjoin?.code)
                    var dialog = AlertDialog.Builder(this@UserJoinActivity)
                    dialog.setTitle(userjoin?.msg)
                    dialog.setMessage(userjoin?.code)
                    dialog.show()
                }
            })
//            startActivity(Intent(this,UserMainActivity::class.java))
        }


        //사장님용 전환 버튼
        binding.UserJoinViewAdminBtn.setOnClickListener {
            startActivity(Intent(this,OwnerJoinActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }
    }
}
