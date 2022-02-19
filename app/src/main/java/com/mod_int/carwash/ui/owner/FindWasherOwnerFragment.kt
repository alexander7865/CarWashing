package com.mod_int.carwash.ui.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.find_recycler_adapter.FindRecyclerAdapter
import com.mod_int.carwash.find_recycler_adapter.WasherInfo
import com.mod_int.carwash.databinding.FragmentFindWasherOwnerBinding

class FindWasherOwnerFragment : Fragment() {

    lateinit var binding: FragmentFindWasherOwnerBinding
    private val findAdapter = FindRecyclerAdapter()
    private val findRecycler : RecyclerView by lazy {binding.findWasherRecycler}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindWasherOwnerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findRecycler.run {
            adapter = findAdapter
        }

        val myData = WasherInfo( //리사이클러뷰 초기값 설정
            name = "홍길동",
            count = 5,
            point = 90,
            deliPrice = 6000,
            policyPrice = 20000,
            far = 1,
            time = 20,
            playTime = "24시간 작업가능"
        )

        val myList = mutableListOf<WasherInfo>().apply {
            for (i in 0..100) {
                add(myData)
            }
        }
        findAdapter.addAll(myList)
        findAdapter.setItemClickListener { item ->

        }
    }
}