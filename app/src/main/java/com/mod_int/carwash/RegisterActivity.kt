package com.mod_int.carwash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityRegisterBinding
import com.mod_int.carwash.ui.owner.OwnerActivity
import com.mod_int.carwash.ui.washer.WasherActivity
import java.util.regex.Pattern

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {

    private var auth : FirebaseAuth? = null
    private var fireStore : FirebaseFirestore? = null

    private val authListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if(user != null){
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
        with(binding){
            btnRegister.setOnClickListener {
                var isExistBlank = false
                val em = binding.inputEmailRegister.text.toString()
                val pw = binding.inputPassRegister.text.toString()
                val pwRe = binding.inputCfmPassRegister.text.toString()
                val ownerMember = intent.getStringExtra("오너회원")
                val washerMember = intent.getStringExtra("워셔회원")

                if(em.isEmpty() || pw.isEmpty() || pwRe.isEmpty()){
                    isExistBlank = true
                }
                //오너가입 로직
                if (ownerMember != null) {
                    if (!isExistBlank && checkEmailRegister() && checkPassword() && checkPasswordLength()) {
                        inputEmailRegister.isEnabled = false
                        inputPassRegister.isEnabled = false
                        inputCfmPassRegister.isEnabled = false
                        createUserId(
                            "${ownerMember}${binding.inputEmailRegister.text}",
                            inputPassRegister.text.toString())
                        val intent = Intent(this@RegisterActivity, OwnerActivity::class.java)
                        startActivity(intent)
                        Log.d("회원구분", "${ownerMember}${binding.inputEmailRegister.text}")
                    } else if (!checkEmailRegister()) {
                        inputEmailRegister.editableText.clear()
                        val toastCenter = Toast.makeText(
                            this@RegisterActivity,
                            "이메일이 형식에 맞지 않습니다.\n재작성 해주세요!",
                            Toast.LENGTH_SHORT
                        )
                        toastCenter.setGravity(
                            Gravity.CENTER,
                            Gravity.CENTER_HORIZONTAL,
                            0
                        )
                        toastCenter.show()
                    } else if (!checkPassword()) {
                        inputCfmPassRegister.editableText.clear()
                        inputPassRegister.editableText.clear()
                        val toastCenter = Toast.makeText(
                            this@RegisterActivity,
                            "비밀번호가 일치하지 않습니다.\n재작성 해주세요!",
                            Toast.LENGTH_SHORT
                        )
                        toastCenter.setGravity(
                            Gravity.CENTER,
                            Gravity.CENTER_HORIZONTAL,
                            0
                        )
                        toastCenter.show()

                    } else {
                        inputCfmPassRegister.editableText.clear()
                        inputPassRegister.editableText.clear()
                        val toastCenter = Toast.makeText(
                            this@RegisterActivity,
                            "비밀번호는 8자리 이상으로 입력하셔야 합니다.재작성 해주세요!",
                            Toast.LENGTH_SHORT
                        )
                        toastCenter.setGravity(
                            Gravity.CENTER,
                            Gravity.CENTER_HORIZONTAL,
                            0
                        )
                        toastCenter.show()
                    }
                }else{
                    if (!isExistBlank && checkEmailRegister() && checkPassword() && checkPasswordLength()) {
                        inputEmailRegister.isEnabled = false
                        inputPassRegister.isEnabled = false
                        inputCfmPassRegister.isEnabled = false
                        createUserId(
                            "${washerMember}${binding.inputEmailRegister.text}",
                            inputPassRegister.text.toString())
                        val intent = Intent(this@RegisterActivity, WasherActivity::class.java)
                        startActivity(intent)
                        Log.d("회원구분", "${washerMember}${binding.inputEmailRegister.text}")
                    } else if (!checkEmailRegister()) {
                        inputEmailRegister.editableText.clear()
                        val toastCenter = Toast.makeText(
                            this@RegisterActivity,
                            "이메일이 형식에 맞지 않습니다.\n재작성 해주세요!",
                            Toast.LENGTH_SHORT
                        )
                        toastCenter.setGravity(
                            Gravity.CENTER,
                            Gravity.CENTER_HORIZONTAL,
                            0
                        )
                        toastCenter.show()
                    } else if (!checkPassword()) {
                        inputCfmPassRegister.editableText.clear()
                        inputPassRegister.editableText.clear()
                        val toastCenter = Toast.makeText(
                            this@RegisterActivity,
                            "비밀번호가 일치하지 않습니다.\n재작성 해주세요!",
                            Toast.LENGTH_SHORT
                        )
                        toastCenter.setGravity(
                            Gravity.CENTER,
                            Gravity.CENTER_HORIZONTAL,
                            0
                        )
                        toastCenter.show()

                    } else {
                        inputCfmPassRegister.editableText.clear()
                        inputPassRegister.editableText.clear()
                        val toastCenter = Toast.makeText(
                            this@RegisterActivity,
                            "비밀번호는 8자리 이상으로 입력하셔야 합니다.재작성 해주세요!",
                            Toast.LENGTH_SHORT
                        )
                        toastCenter.setGravity(
                            Gravity.CENTER,
                            Gravity.CENTER_HORIZONTAL,
                            0
                        )
                        toastCenter.show()
                    }
                }
            }


            inputEmailRegister.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
                override fun afterTextChanged(s: Editable?) {
                    checkEmailRegister()
                }
            })

            inputPassRegister.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    checkPasswordLength()
                }
            })

            inputCfmPassRegister.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
                override fun afterTextChanged(s: Editable?) {
                    checkPassword()
                }
            })

            btnCancelRegister.setOnClickListener {
                onBackPressed()
                overridePendingTransition(0, 0) //애니메이션 효과없에기
            }
        }
    }

    private fun createUserId(email:String, password:String){
        auth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(this){
            if(it.isSuccessful){
                Log.d("결과", "가입성공")
            }else{
                Log.d("결과", "가입실패")
            }
        }
    }

    private fun checkEmailRegister() : Boolean{
        var emailValidation = "^[A-Za-z0-9]([._-]?[A-Za-z0-9])*@[A-Za-z0-9]([._-]?[A-Za-z0-9])*\\.[A-Za-z]{2,}$"
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

    private fun checkPassword() : Boolean{
        var pw = binding.inputPassRegister.text.toString()
        var pwRe = binding.inputCfmPassRegister.text.toString()
        return if(pw == pwRe){
            //패스워드가 같을경우
            Log.d("입력비밀번호확인", "일치 합니다")
            true
        }else{
            Log.d("입력비밀번호확인", "일치하지 않습니다")
            false
        }
    }

    private fun checkPasswordLength() : Boolean{
        var pwLength = binding.inputPassRegister.text.toString()
        return if(pwLength.length > 7 ){
            //패스워드가 형식에 부합될 경우
            Log.d("입력비밀번호길이", "일치 합니다")
            true
        }else{
            //패스워드가 부합되지 않을경우
            Log.d("입력비밀번호길이", "일치하지 않습니다")
            false
        }
    }
}