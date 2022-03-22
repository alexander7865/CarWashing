package com.mod_int.carwash.ui.owner_member

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmHomeBinding


class OmHomeFragment : BaseFragment<FragmentOmHomeBinding>(R.layout.fragment_om_home) {

    private lateinit var ownerActivity: OmActivity
    private val db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()


    override fun onStart() {
        super.onStart()

        //파이어베이스 에서 데이터 가지고오는것 까지만 성공 했습니다 이후 데이터에관하여 가지고 오는 방법을 모르겠네요
        //또한 데이터를 가지고 와서 보여줄경우 화면이 깜빡이는 현상이 일어 납니다... 왜 그런걸까요??
        //한번 받아오고 계속 갱신하는 문제인듯 하네요 뷰모델을 사용하면 이런일이 없어지겠지요?
        val user = auth.currentUser?.email
        user?.let {
            db.collection("OwnerMember")
                .document("$user")
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("차주이메일", document.get("email") as String)
                        with(binding){
                            omDate.text = document.get("email") as String
                            omPhoneNr.text = document.get("phoneNumber") as String
                            omCarInfo.text = document.get("type") as String
                        }
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






