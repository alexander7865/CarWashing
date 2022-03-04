package com.mod_int.carwash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityRegisterBinding
import com.mod_int.carwash.ui.owner.OwnerActivity
import com.mod_int.carwash.ui.washer.WasherActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {

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


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()
        auth?.signOut()

        // 회원가입은 간단하게 구현해봤습니다
        // 하지만 문제가 생기네요 이메일이 중복가입이 됩니다.
        // ㅠㅠ 이럴경우 어떻게 해야하는지요?? (회원가입에 대해 구글링을 해봤지만 너무 어렵네요ㅠㅠ)
        with(binding) {
            btnRegister.setOnClickListener {
                register()
            }

            btnCancelRegister.setOnClickListener {
                onBackPressed()
                overridePendingTransition(0, 0) //애니메이션 효과없에기
            }
        }
    }

    private fun register() {
        enableSetting(false)
        val ownerMember = intent.getStringExtra("오너회원")
        val washerMember = intent.getStringExtra("워셔회원")

        if (ownerMember != null) {
            CoroutineScope(Main).launch {
                val checkEmail = async { checkEmail() }
                val checkPassword = async { checkPassword() }
                val checkEqualPassword = async { checkEqualPassword() }
                if (checkEmail.await() && checkPassword.await() && checkEqualPassword.await()) {
                    createUserId(
                        "${ownerMember}${binding.inputEmailRegister.text}",
                        binding.inputPassRegister.text.toString()
                    ) { isRegister ->
                        if (isRegister) {
                            val intent =
                                Intent(this@RegisterActivity, OwnerActivity::class.java)
                            startActivity(intent)
                            Log.d(
                                "회원구분",
                                "${ownerMember}${binding.inputEmailRegister.text}"
                            )
                        } else {
                            showToast(message = "중복된 아이디입니다.")
                        }
                    }
                }
                enableSetting(true)
            }
        } else {
            CoroutineScope(Main).launch {
                val checkEmail = async { checkEmail() }
                val checkPassword = async { checkPassword() }
                val checkEqualPassword = async { checkEqualPassword() }
                if (checkEmail.await() && checkPassword.await() && checkEqualPassword.await()) {
                    createUserId(
                        "${ownerMember}${binding.inputEmailRegister.text}",
                        binding.inputPassRegister.text.toString()
                    ) { isRegister ->
                        if (isRegister) {
                            val intent =
                                Intent(this@RegisterActivity, WasherActivity::class.java)
                            startActivity(intent)
                            Log.d(
                                "회원구분",
                                "${washerMember}${binding.inputEmailRegister.text}"
                            )
                        } else {
                            showToast(message = "중복된 아이디입니다.")
                        }
                    }
                }
                enableSetting(true)
            }
        }
    }


    private fun createUserId(email: String, password: String, callback: (Boolean) -> Unit) {
        auth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(this) {
            callback(it.isSuccessful)
        }
    }


    private fun checkEmail(): Boolean {
        val email = binding.inputEmailRegister.text.toString()
        return when {
            !checkEmailRegister() -> {
                showToast(message = "이메일이 형식에 맞지 않습니다.\n재작성 해주세요!")
                false
            }
            email.isEmpty() -> {
                showToast(message = "이메일을 입력해주세요.")
                false
            }
            else -> true
        }
    }


    private fun checkPassword(): Boolean {
        val password = binding.inputPassRegister.text.toString()
        return when {
            password.isEmpty() -> {
                showToast(message = "비밀번호를 입력하세요.")
                false
            }

            password.length <= 7 -> {
                showToast(message = "비밀번호는 8자리 이상으로 입력하셔야 합니다.재작성 해주세요!")
                false
            }
            else -> true
        }
    }

    private fun checkEqualPassword(): Boolean {
        return if (binding.inputCfmPassRegister.text.toString() != binding.inputPassRegister.text.toString()) {
            showToast(message = "비밀번호가 일치하지 않습니다.\n재작성 해주세요!")
            false
        } else {
            true
        }
    }

    private fun checkEmailRegister(): Boolean {
        var emailValidation =
            "^[A-Za-z0-9]([._-]?[A-Za-z0-9])*@[A-Za-z0-9]([._-]?[A-Za-z0-9])*\\.[A-Za-z]{2,}$"
        var email = binding.inputEmailRegister.text.toString().trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 이메일이 정규식형식과 맞는지 확인하는지 선언
        return if (p) {
            //이메일 형태가 정상일 경우
            Log.d("입력이메일", "정규 형식입니다")
            true
        } else {
            Log.d("입력이메일", "정규 형식이 아닙니다")
            false
        }
    }

    private fun enableSetting(isEnable: Boolean) {
        with(binding) {
            inputEmailRegister.isEnabled = isEnable
            inputPassRegister.isEnabled = isEnable
            inputCfmPassRegister.isEnabled = isEnable
        }
    }


}


fun AppCompatActivity.showToast(message: String) {
    val toastCenter = Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    )
    toastCenter.setGravity(
        Gravity.CENTER,
        Gravity.CENTER_HORIZONTAL,
        0
    )
    toastCenter.show()
}
