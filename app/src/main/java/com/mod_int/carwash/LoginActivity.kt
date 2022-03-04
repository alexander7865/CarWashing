package com.mod_int.carwash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private var auth: FirebaseAuth? = null
    private var fireStore: FirebaseFirestore? = null

    private val authListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if (user != null) {
            Log.d("결과", "로그인상태 O")
        } else {
            Log.d("결과", "로그인상태 X")
        }
    }

    override fun onStart() {
        super.onStart()
        auth?.addAuthStateListener(authListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()
        auth?.signOut()

        with(binding) {
            btnLoginOwner.setOnClickListener { login() }
            btnCancelLogin.setOnClickListener {
                onBackPressed()
                overridePendingTransition(0, 0) //애니메이션 효과없에기
            }
            binding.inputPassLogin.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login()
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun login() {
        with(binding){
            inputEmailLogin.isEnabled = false
            inputPassLogin.isEnabled = false
            auth?.signInWithEmailAndPassword(
                inputEmailLogin.text.toString(),
                inputPassLogin.text.toString()
            )?.addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this@LoginActivity, MemberTypeActivity::class.java)
                    intent.putExtra("이메일", "${inputEmailLogin.text}")
                    startActivity(intent)
                } else {
                    inputEmailLogin.isEnabled = true
                    inputPassLogin.isEnabled = true
                    val toastCenter = Toast.makeText(
                        this@LoginActivity, "이메일주소 또는 비밀번호를 확인해주세요!", Toast.LENGTH_SHORT
                    )
                    toastCenter.setGravity(Gravity.CENTER, 0, 0)
                    toastCenter.show()
                }
            }
        }
    }
}