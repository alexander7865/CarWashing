package com.mod_int.carwash.ui.owner

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentJoinOwnerBinding

class JoinOwnerFragment : BaseFragment<FragmentJoinOwnerBinding>(R.layout.fragment_join_owner),
    View.OnClickListener {

    lateinit var ownerActivity: OwnerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //버튼 가지고와서 클릭리스너 구현
        with(binding) {
            btnCancelRegistration.setOnClickListener(this@JoinOwnerFragment)
            btnSaveJoin.setOnClickListener(this@JoinOwnerFragment)
            btnCancelJoin.setOnClickListener(this@JoinOwnerFragment)
            btnRegistrationJoin.setOnClickListener(this@JoinOwnerFragment)
        }



        //리스트에 담고 싶은데 안되네요 정보를 리스트에 담고 싶으나 안되네요
//        val items = ArrayList<String>()
//        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items)
//        binding.carListOwner.adapter = arrayAdapter
//        binding.carListOwner.choiceMode = ListView.CHOICE_MODE_SINGLE
//        binding.carListOwner.setOnClickListener {
//            val check = binding.carListOwner.checkedItemPosition
//            if(check > -1) {
//                items.removeAt(check)
//                binding.carListOwner.clearChoices()
//                arrayAdapter.notifyDataSetChanged()
//            }
//        }
    }

    //버튼 클릭 구현
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_cancel_registration -> {
                ownerActivity.backStep()
            }
            R.id.btn_save_join -> {
                var carNum = binding.btnCarNum.text.toString()
                var carBrand = binding.btnCarBrand.text.toString()
                var carModel = binding.btnCarModel.text.toString()
                var carKinds = binding.btnCarKinds.text.toString()
                var carCol = binding.btnCarCol.text.toString()
                

            }
            R.id.btn_cancel_join -> {

            }
            R.id.btn_registration_join -> {

            }
        }
    }
}
