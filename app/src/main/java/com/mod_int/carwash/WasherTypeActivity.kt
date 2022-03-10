package com.mod_int.carwash

import android.content.Intent
import android.os.Bundle
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityWasherTypeBinding

class WasherTypeActivity : BaseActivity<ActivityWasherTypeBinding>(R.layout.activity_washer_type) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.headWasher.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("헤드워셔","headWasher")
            startActivity(intent)
            overridePendingTransition(0, 0)

        }

        binding.pickupWasher.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("픽업워셔","pickupWasher")
            startActivity(intent)
            overridePendingTransition(0, 0)

        }

        binding.btnCancelWasherType.setOnClickListener {
            onBackPressed()
            overridePendingTransition(0, 0)
        }
    }
}