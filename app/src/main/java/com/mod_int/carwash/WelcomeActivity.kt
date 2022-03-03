package com.mod_int.carwash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityWelcomeBinding
import com.mod_int.carwash.ui.owner.HomeOwnerFragment
import com.mod_int.carwash.ui.owner.OwnerActivity
import com.mod_int.carwash.ui.washer.WasherActivity

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.goOwner.setOnClickListener {
            val email = intent.getStringExtra("이메일")
            val intent = Intent(this, OwnerActivity::class.java)
            intent.putExtra("email","$email")
            startActivity(intent)
            Log.d("전달값", "$email")

        }

        binding.goWasher.setOnClickListener {
            val intent = Intent(this, WasherActivity::class.java)
            startActivity(intent)
        }
    }
}