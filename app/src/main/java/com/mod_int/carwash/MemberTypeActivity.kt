package com.mod_int.carwash

import android.content.Intent
import android.os.Bundle
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityMemberTypeBinding

class MemberTypeActivity : BaseActivity<ActivityMemberTypeBinding>(R.layout.activity_member_type) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.goOwner.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("오너회원","owner")
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.goWasher.setOnClickListener {
            val intent = Intent(this, WasherTypeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.btnCancelMemberType.setOnClickListener {
            onBackPressed()
            overridePendingTransition(0, 0)
        }
    }
}
