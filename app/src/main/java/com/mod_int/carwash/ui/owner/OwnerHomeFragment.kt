package com.mod_int.carwash.ui.owner

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.mod_int.carwash.R
import com.mod_int.carwash.RegisterActivity
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOwnerHomeBinding


class OwnerHomeFragment : BaseFragment<FragmentOwnerHomeBinding>(R.layout.fragment_owner_home) {

    private lateinit var ownerActivity: OwnerActivity
    private var fireStore: FirebaseFirestore? = null
    private var auth: FirebaseAuth? = null


    override fun onStart() {
        super.onStart()
        //파이어베이스 어스에서 데이터 가지고오는것 까지만 성공 했습니다 이후 데이터에관하여 가지고 오는 방법을 모르겠네요
        //또한 데이터를 가지고 와서 보여줄경우 화면이 깜빡이는 현상이 일어 납니다... 왜 그런걸까요??
        fireStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
//        val user = Firebase.auth.currentUser
//        user?.let {
//            val email = user.email
//            fireStore?.collection("ownerMember")
//                ?.document("$email")
//                ?.get()
//                ?.addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        var userDTO = it.result?.toObject(RegisterActivity.User::class.java)
//                        var em = userDTO?.email
//                        var cp = userDTO?.phoneNumber
//                        var tp = userDTO?.type
//
//                        binding.ownerPhoneNr.text = cp
//
//                    }
//                }
//        }
    }





    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OwnerActivity) ownerActivity = context

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

