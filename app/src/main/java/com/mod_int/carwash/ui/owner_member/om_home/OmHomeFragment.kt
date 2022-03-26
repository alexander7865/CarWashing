package com.mod_int.carwash.ui.owner_member.om_home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmHomeBinding
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity
import java.time.LocalDateTime


class OmHomeFragment : BaseFragment<FragmentOmHomeBinding>(R.layout.fragment_om_home) {

    private lateinit var ownerActivity: OmActivity
    private val db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()


    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        //코드수정을 해야 할 것 같습니다. 온스타트 실행 될때마다 실행이 되어 앱이 문제가 되는거 같습니다.
        val user = auth.currentUser!!.email
        user!!.let {
            db.collection("OwnerMember")
                .document("$user")
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()){
                        if(document != null) {
                            with(binding){
                                val current = LocalDateTime.now()
                                omDate.text = "${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일"
                                omPhoneNr.text = "미등록"
                                omCarInfo.text = "미등록"
                                carLocation.text = "미등록"
                            }
                        }else{
                            with(binding){
                                val current = LocalDateTime.now()
                                omDate.text = "${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일"
                                omPhoneNr.text = document?.get("phoneNumber") as String
                                omCarInfo.text = "${document?.get("carNumber") as String} " +
                                        "${document?.get("carBrand") as String} " +
                                        "${document?.get("carModel") as String} " +
                                        "${document?.get("carKinds") as String} " +
                                        "${document?.get("carSize") as String} " +
                                        "${document?.get("carColor") as String}"
                                carLocation.text = document?.get("carLocation") as String

                            }
                        }
                    }else{

                    }
                }

        }
    }





    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OmActivity) ownerActivity = context

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //가입으로 이동
        binding.btnJoin.setOnClickListener {
            ownerActivity.joinRegistration()
//            val toastCenter = Toast.makeText(context,"정확한 정보를 입력하세요!",Toast.LENGTH_SHORT)
//            toastCenter.setGravity(Gravity.CENTER,0,0)
//            toastCenter.show()
        }
    }
}






