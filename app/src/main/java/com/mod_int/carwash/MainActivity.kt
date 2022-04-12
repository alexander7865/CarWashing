package com.mod_int.carwash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityMainBinding
import com.mod_int.carwash.ui.login.LoginActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getHashKey()

        binding.goStart.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

        }

        binding.btnGoMemberType.setOnClickListener {
            val intent = Intent(this, MemberTypeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

        }
    }

    @SuppressLint("PackageManagerGetSignatures")
    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}


