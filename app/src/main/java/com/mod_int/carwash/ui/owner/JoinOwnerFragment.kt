package com.mod_int.carwash.ui.owner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.mod_int.carwash.databinding.FragmentJoinOwnerBinding
import com.mod_int.carwash.ui.washer.WasherActivity

class JoinOwnerFragment : Fragment() {

    lateinit var binding: FragmentJoinOwnerBinding
    lateinit var ownerActivity: OwnerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //호출되면 상위 엑티비티에 플래그먼트 붙여넣기
        binding = FragmentJoinOwnerBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //버튼 가지고와서 클릭리스너 구현
        binding.btnCancelRegistration.setOnClickListener {
            ownerActivity.backStep()
        }

        binding.btnSaveJoin.setOnClickListener {

        }

        binding.btnCancelJoin.setOnClickListener {

        }

        binding.btnRegistrationJoin.setOnClickListener {

        }

        //리스트에 담고 싶은데 안되네요 정보를 리스트에 담고 싶으나 안되네요
//        val items = ArrayList<String>()
//        val arrayAdapter = ArrayAdapter(
//            this, android.R.layout.simple_list_item_single_choice, items)
//        binding.carListOwner.choiceMode = ListView.CHOICE_MODE_SINGLE
//        binding.carListOwner.adapter = arrayAdapter
//        binding.carListOwner.setOnClickListener {
//            val check = binding.carListOwner.checkedItemPosition
//            if(check > -1) {
//                items.removeAt(check)
//                binding.carListOwner.clearChoices()
//                arrayAdapter.notifyDataSetChanged()
//            }
//        }
    }
}