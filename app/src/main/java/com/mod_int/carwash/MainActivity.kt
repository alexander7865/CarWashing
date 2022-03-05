package com.mod_int.carwash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.goStart.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoMemberType.setOnClickListener {
            val intent = Intent(this, MemberTypeActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        }
    }
}