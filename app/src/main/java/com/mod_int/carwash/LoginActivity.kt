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

    private var auth : FirebaseAuth? = null
    private var fireStore : FirebaseFirestore? = null

    private val authListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if(user != null ){
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
            btnLoginOwner.setOnClickListener{
                inputEmailLogin.isEnabled = false
                inputPassLogin.isEnabled = false
                // 키보드에서 Done(완료) 클릭 시 , 원하는 뷰 클릭되게 하기 이게 맞는걸까요?
                inputPassLogin.setOnEditorActionListener(getEditorActionListenerDone(btnLoginOwner))
                btnLoginOwner.setOnClickListener {
                }


                auth?.signInWithEmailAndPassword(inputEmailLogin.text.toString(), inputPassLogin.text.toString())?.
                addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this@LoginActivity, MemberTypeActivity::class.java)
                        intent.putExtra("이메일","${inputEmailLogin.text}")
                        startActivity(intent)
                    }else{
                        inputEmailLogin.isEnabled = true
                        inputPassLogin.isEnabled = true
                        val toastCenter = Toast.makeText(
                            this@LoginActivity,"이메일주소 또는 비밀번호를 확인해주세요!", Toast.LENGTH_SHORT)
                        toastCenter.setGravity(Gravity.CENTER,0,0)
                        toastCenter.show()
                    }
                }
            }
        }

        binding.btnCancelLogin.setOnClickListener {
            onBackPressed()
            overridePendingTransition(0, 0) //애니메이션 효과없에기
        }
    }

    private fun getEditorActionListenerDone(view: View): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.callOnClick()
            }
            false
        }
    }


//    private fun createUserId(email:String, password:String){
//        auth?.createUserWithEmailAndPassword(email,password)?.addOnCompleteListener(this){
//            if(it.isSuccessful){
//                Log.d("결과", "로그인성공")
//            }else{
//                Log.d("결과", "로그인실패")
//
//            }
//        }
//    }
//
//    //로그인시 토근값이 살아있는동안은 계속적으로 로그인이 되어있는것으로 판단.
//    private fun loginUserId(email: String, password: String) {
//        auth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
//            if (it.isSuccessful) {
//                Log.d("결과", "로그인성공")
//                //성공
//            } else {
//                Log.d("결과", "로그인실패")
//                //실패
//            }
//        }
//    }
//
//    //검증하고 나서 다시 로그인을 해줘야 true 로 바뀐다.
//    private fun isEmailVerifiedCurrentUser(): Boolean =
//        auth?.currentUser?.isEmailVerified ?: false
//
//    //검증하라는 메일을 보내는 파라메터
//    private fun verifyEmail() {
//        auth?.currentUser?.sendEmailVerification()?.addOnCompleteListener {
//            if (it.isSuccessful) {
//                Log.d("결과", "검증 파라메터 전송 성공")
//            } else {
//                Log.d("결과", "검증 파라메터 전송 실패")
//            }
//        }
//    }
//
//    private fun updatePassword(password: String) {
//        auth?.currentUser?.updatePassword(password)?.addOnCompleteListener {
//            if (it.isSuccessful) {
//                Log.d("결과", "비밀번호 변경 성공")
//            } else {
//                Log.d("결과", "비밀번호 변경 실패")
//            }
//        }
//    }
//
//
//    //초기화 메일에서 비밀번호 변경하지 않으면 무시해도 된다.
//    //바꿔야지만 반영
//    //바꾸고 나서 재로그인 안한 상태로 현재 로그인된 아이디는 유지되는것으로 판단.
//    private fun resetPassword(email: String) {
//        auth?.sendPasswordResetEmail(email)?.addOnCompleteListener {
//            if (it.isSuccessful) {
//                Log.d("결과", "비밀번호 초기화 성공")
//            } else {
//                Log.d("결과", "비밀번호 초기화 실패")
//            }
//        }
//    }
//
//
//    // 계정 삭제.
//    private fun deleteUser() {
//        auth?.currentUser?.delete()?.addOnCompleteListener {
//            if (it.isSuccessful) {
//                Log.d("결과", "계정 삭제 성공")
//            } else {
//                Log.d("결과", "계정 삭제 실패")
//            }
//        }
//    }
//
//    private fun reAuthenticate() {
//        val credential = EmailAuthProvider.getCredential("alexander@mod-int.com", "12341234")
//        auth?.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
//
//            if (it.isComplete) {
//                Log.d("결과", "재인증 성공")
//            } else {
//                Log.d("결과", "재인증 실패")
//            }
//        }
//    }
//
//    private fun logout() {
//        auth?.signOut()
//    }
}