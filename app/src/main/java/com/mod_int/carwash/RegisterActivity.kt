package com.mod_int.carwash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityRegisterBinding
import com.mod_int.carwash.ui.owner.OwnerActivity
import com.mod_int.carwash.ui.pickup_manager.PickupManagerActivity
import com.mod_int.carwash.ui.washer.WasherActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

        //버튼 클릭시 발동 구현 + 함수로 정리
        with(binding) {
            btnRegister.setOnClickListener {
                register()
            }

            btnCancelRegister.setOnClickListener {
                onBackPressed()
                overridePendingTransition(0, 0) //애니메이션 효과없에기
            }

            //키보드 done 버튼 클릭 당장 필요없음
//            inputCfmPassRegister.setOnEditorActionListener { _, actionId, _ ->
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    register()
//                    true
//                } else {
//                    false
//                }
//            }
        }
    }

    private fun register() {
        enableSetting(false)
        val ownerMember = intent.getStringExtra("오너회원")
        val headWasherMember = intent.getStringExtra("헤드워셔")
        val pickupWasherMember = intent.getStringExtra("픽업워셔")

        when {
            (ownerMember != null) -> {
                CoroutineScope(Dispatchers.Main).launch {
                    //휴대폰 번호 추가시에 이쪽에서 추가 하면 됨
                    val checkEmail = async { checkEmail() }
                    val checkPassword = async { checkPassword() }
                    val checkEqualPassword = async { checkEqualPassword() }
                    if (checkEmail.await() && checkPassword.await() && checkEqualPassword.await()) {
                        enableSetting(false)
                        createUserId(
                            binding.inputEmailRegister.text.toString(),
                            binding.inputPassRegister.text.toString()
                        ) { isRegister ->
                            if (isRegister) {
                                val user = User(
                                    email = binding.inputEmailRegister.text.toString(),
                                    phoneNumber = "01082277865", // 임의 번호 생성해줌
                                    password = binding.inputPassRegister.text.toString()
                                )
                                fireStore?.collection("Owner")?.document("UserInfo")
                                    ?.set(emptyMap<String, User>())?.addOnCompleteListener {
                                        if(it.isSuccessful){
                                            fireStore?.collection("Owner")
                                                ?.document(
                                                    "UserInfo"
                                                )?.update("list", FieldValue.arrayUnion(user))
                                                ?.addOnCompleteListener {
                                                    if (it.isSuccessful) {
                                                        val intent =
                                                            Intent(
                                                                this@RegisterActivity,
                                                                OwnerActivity::class.java
                                                            )
                                                        startActivity(intent)
                                                    } else {
                                                        enableSetting(true)
                                                        Toast.makeText(
                                                            this@RegisterActivity,
                                                            "유저 정보 등록을 실패하였습니다. 다시시도해 주세요.",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                        }
                                    }
                            } else {
                                enableSetting(true)
                                showToast(message = "중복된 아이디입니다.")
                            }
                        }
                    }
                }
            }
            (headWasherMember != null) -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val checkEmail = async { checkEmail() }
                    val checkPassword = async { checkPassword() }
                    val checkEqualPassword = async { checkEqualPassword() }
                    if (checkEmail.await() && checkPassword.await() && checkEqualPassword.await()) {
                        enableSetting(false)
                        createUserId(
                            binding.inputEmailRegister.text.toString(),
                            binding.inputPassRegister.text.toString()
                        ) { isRegister ->
                            if (isRegister) {
                                val user = User(
                                    email = binding.inputEmailRegister.text.toString(),
                                    phoneNumber = "01022222222",
                                    password = binding.inputPassRegister.text.toString()
                                )


                                fireStore?.collection("HeadWasher")
                                    ?.document(
                                        "UserInfo"
                                    )?.update("list", FieldValue.arrayUnion(user))
                                    ?.addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            val intent = Intent(
                                                this@RegisterActivity,
                                                WasherActivity::class.java
                                            )
                                            startActivity(intent)
                                        } else {
                                            enableSetting(true)
                                            Toast.makeText(
                                                this@RegisterActivity,
                                                "유저 정보 등록을 실패하였습니다. 다시시도해 주세요.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            } else {
                                enableSetting(true)
                                showToast(message = "중복된 아이디입니다.")
                            }
                        }
                    }
                }
            }
            (pickupWasherMember != null) -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val checkEmail = async { checkEmail() }
                    val checkPassword = async { checkPassword() }
                    val checkEqualPassword = async { checkEqualPassword() }
                    if (checkEmail.await() && checkPassword.await() && checkEqualPassword.await()) {
                        enableSetting(false)
                        createUserId(
                            binding.inputEmailRegister.text.toString(),
                            binding.inputPassRegister.text.toString()
                        ) { isRegister ->
                            if (isRegister) {
                                val user = User(
                                    email = binding.inputEmailRegister.text.toString(),
                                    phoneNumber = "01022222222",
                                    password = binding.inputPassRegister.text.toString()
                                )
                                fireStore?.collection("PickUpWasher")
                                    ?.document(
                                        "UserInfo"
                                    )?.update("list", FieldValue.arrayUnion(user))
                                    ?.addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            val intent = Intent(
                                                this@RegisterActivity,
                                                PickupManagerActivity::class.java
                                            )
                                            startActivity(intent)
                                        } else {
                                            enableSetting(true)
                                            Toast.makeText(
                                                this@RegisterActivity,
                                                "유저 정보 등록을 실패하였습니다. 다시시도해 주세요.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            } else {
                                enableSetting(true)
                                showToast(message = "중복된 아이디입니다.")
                            }
                        }
                    }
                }
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
                enableSetting(true)
                showToast(message = "이메일이 형식에 맞지 않습니다.\n재작성 해주세요!")
                false
            }
            email.isEmpty() -> {
                enableSetting(true)
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
                enableSetting(true)
                showToast(message = "비밀번호를 입력하세요.")
                false
            }
            password.length <= 7 -> {
                enableSetting(true)
                showToast(message = "비밀번호는 8자리 이상으로 입력하셔야 합니다.재작성 해주세요!")
                false
            }
            else -> true
        }
    }

    private fun checkEqualPassword(): Boolean {
        return if (binding.inputCfmPassRegister.text.toString() != binding.inputPassRegister.text.toString()) {
            enableSetting(true)
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
        val p = Pattern.matches(emailValidation, email) // 이메일이 정규형식과 맞는지 확인하는 선언
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

    data class User(
        var email: String = "",
        var password: String = "",
        var phoneNumber: String = "",
    )
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