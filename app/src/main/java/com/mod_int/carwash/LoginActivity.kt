package com.mod_int.carwash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityLoginBinding
import com.mod_int.carwash.ui.owner.OwnerActivity
import com.mod_int.carwash.ui.pickup_manager.PickupManagerActivity
import com.mod_int.carwash.ui.washer.WasherActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
        auth = FirebaseAuth.getInstance() //파이어베이스 로그인 접근권한 가지고 오기
        fireStore = FirebaseFirestore.getInstance() //파이어스토어 접근권한 가지고 오기
        auth?.signOut()

        with(binding) {
            btnLoginOwner.setOnClickListener {
                login()
                overridePendingTransition(0, 0) //애니메이션 효과없에기

            }
            btnCancelLogin.setOnClickListener {
                onBackPressed()
                overridePendingTransition(0, 0) //애니메이션 효과없에기
            }
            inputPassLogin.setOnEditorActionListener { _, actionId, _ ->
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
            enableSetting(false)
                CoroutineScope(Dispatchers.Main).launch {
                    val checkEmail = async { checkEmail() }
                    val checkPassword = async { checkPassword() }

                    if (checkEmail.await() && checkPassword.await()) {
                        auth?.signInWithEmailAndPassword(
                            inputEmailLogin.text.toString(),
                            inputPassLogin.text.toString()
                        )?.addOnCompleteListener { it ->

                        if (it.isSuccessful) {
                            fireStore?.collection(inputEmailLogin.text.toString())?.document(
                                "UserInfo")?.get()?.addOnCompleteListener {
                                if (it.isSuccessful) {
                                    //파이어스토어에서 결과값 가지고 오기
                                    val userDTO = it.result?.toObject(RegisterActivity.User::class.java)
                                    Log.d(
                                        "결과",
                                        "email : ${userDTO?.email} phoneNumber : ${userDTO?.phoneNumber} type : ${userDTO?.type}"
                                    )
                                    when {
                                        (userDTO?.type == "owner") -> {
                                            val intent = Intent(this@LoginActivity, OwnerActivity::class.java)
                                            startActivity(intent)
                                        }
                                        (userDTO?.type == "headWasher") -> {
                                            val intent = Intent(this@LoginActivity, WasherActivity::class.java)
                                            startActivity(intent)
                                        }
                                        (userDTO?.type == "pickupWasher") -> {
                                            val intent = Intent(this@LoginActivity, PickupManagerActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }
                                }
                            }
                        } else {
                            enableSetting(true)
                            showToast(message = "이메일주소 또는 비밀번호를 확인해주세요!")
                        }
                    }
                }
            }
        }
    }

    private fun enableSetting(isEnable: Boolean) {
        with(binding) {
            inputEmailLogin.isEnabled = isEnable
            inputPassLogin.isEnabled = isEnable
        }
    }

    private fun checkEmail(): Boolean {
        val email = binding.inputEmailLogin.text.toString()
        return when {
            email.isEmpty() -> {
                enableSetting(true)
                showToast(message = "이메일과 비밀번호를 모두 입력해주세요.")
                false
            }
            else -> true
        }
    }


    private fun checkPassword(): Boolean {
        val password = binding.inputPassLogin.text.toString()
        return when {
            password.isEmpty() -> {
                enableSetting(true)
                showToast(message = "이메일과 비밀번호를 모두 입력해주세요.")
                false
            }
            else -> true
        }
    }
}