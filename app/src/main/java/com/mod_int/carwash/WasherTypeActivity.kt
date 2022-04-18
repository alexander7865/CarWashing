package com.mod_int.carwash

import android.content.Intent
import android.os.Bundle
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityWasherTypeBinding
import com.mod_int.carwash.ui.register.RegisterActivity

class WasherTypeActivity : BaseActivity<ActivityWasherTypeBinding>(R.layout.activity_washer_type) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.headWasher.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra(RegisterActivity.KEY_TYPE, "washerMember")
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.pickupWasher.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra(RegisterActivity.KEY_TYPE, "pickupMember")
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.btnCancelWasherType.setOnClickListener {
            onBackPressed()
            overridePendingTransition(0, 0)
        }
    }
}