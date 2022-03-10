package com.mod_int.carwash.ui.owner

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOwnerFindWasherBinding
import com.mod_int.carwash.ui.owner.find_recycler_view.FindRecyclerAdapter
import com.mod_int.carwash.ui.owner.find_recycler_view.WasherInfo

class OwnerFindWasherFragment : BaseFragment<FragmentOwnerFindWasherBinding>(R.layout.fragment_owner_find_washer) {

    private val findAdapter = FindRecyclerAdapter()
    private val findRecycler : RecyclerView by lazy {binding.findWasherRecycler}
    val db = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //리사이클러뷰에 파이어베이스 데이터를 넣고 싶은데 잘 안됩니다.

        findRecycler.run {
            adapter = findAdapter
        }

        val myData = WasherInfo( //리사이클러뷰 초기값 설정
            name = "홍길동",
            count = 5,
            point = 90,
            deliPrice = 6000,
            policyPrice = 20000,
            location = "압구정동 3동"
        )

        val myList = mutableListOf<WasherInfo>().apply {
            for (i in 0..100) {
                add(myData)
            }
        }

        findAdapter.addAll(myList)
        findAdapter.setItemClickListener {item ->

        }

        //새로고침 구현
        with(binding) {
            pullToRefresh.setOnRefreshListener {
                pullToRefresh.isRefreshing = false
                findAdapter.addAll(myList)

            }
        }
    }
}