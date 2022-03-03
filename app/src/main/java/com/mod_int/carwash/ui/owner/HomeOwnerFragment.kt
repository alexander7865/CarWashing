package com.mod_int.carwash.ui.owner

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentHomeOwnerBinding


class HomeOwnerFragment : BaseFragment<FragmentHomeOwnerBinding>(R.layout.fragment_home_owner) {

    private lateinit var ownerActivity: OwnerActivity

    override fun onAttach(context: Context) {

        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
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