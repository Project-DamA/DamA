package com.dama.Login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dama.Login.databinding.ActivityDetailCafeBinding

class DetailCafeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCafeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailCafeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}