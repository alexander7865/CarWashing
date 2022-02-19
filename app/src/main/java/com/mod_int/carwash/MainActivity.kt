package com.mod_int.carwash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityMainBinding
import com.mod_int.carwash.ui.owner.JoinOwnerFragment
import com.mod_int.carwash.ui.owner.OwnerActivity
import com.mod_int.carwash.ui.washer.WasherActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.ownerMove.setOnClickListener {
            val intent = Intent(this, OwnerActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0) //애니메이션 효과없에기
        }

        binding.washerMove.setOnClickListener {
            val intent = Intent(this, WasherActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0) //애니메이션 효과없에기
        }
    }
}