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
            intent.putExtra("오너회원","오너")
            startActivity(intent)
        }

        binding.goWasher.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("워셔회원","워셔")
            startActivity(intent)
        }

        binding.btnCancelMemberType.setOnClickListener {
            onBackPressed()
            overridePendingTransition(0, 0)
        }
    }
}
