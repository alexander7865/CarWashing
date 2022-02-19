package com.mod_int.carwash.ui.owner

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.FragmentHomeOwnerBinding


class HomeOwnerFragment : Fragment() {

    lateinit var binding: FragmentHomeOwnerBinding
    private lateinit var ownerActivity: OwnerActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
    }


    //페이지를 붙이는 구현
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeOwnerBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //가입으로 이동
        binding.btnJoin.setOnClickListener {
            ownerActivity.joinRegistration()
            val toastCenter = Toast.makeText(context,"정확한 정보를 입력하세요!",Toast.LENGTH_SHORT)
            toastCenter.setGravity(Gravity.CENTER,0,0)
            toastCenter.show()
        }
    }
}