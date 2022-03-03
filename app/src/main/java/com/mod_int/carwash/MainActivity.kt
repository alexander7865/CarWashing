package com.mod_int.carwash

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityMainBinding
import com.mod_int.carwash.ui.owner.OwnerActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.goStart.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0) //애니메이션 효과없에기
        }

        binding.btnGoRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0) //애니메이션 효과없에기
        }
    }
}