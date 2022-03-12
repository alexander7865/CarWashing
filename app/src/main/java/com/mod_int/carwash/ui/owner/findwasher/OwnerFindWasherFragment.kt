package com.mod_int.carwash.ui.owner.findwasher

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOwnerFindWasherBinding
import com.mod_int.carwash.ui.owner.OrderStatusOwnerFragment
import com.mod_int.carwash.ui.owner.find_recycler_view.ClickType
import com.mod_int.carwash.ui.owner.find_recycler_view.FindRecyclerAdapter
import com.mod_int.carwash.ui.owner.find_recycler_view.WasherInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerFindWasherFragment :
    BaseFragment<FragmentOwnerFindWasherBinding>(R.layout.fragment_owner_find_washer) {

    private val ownerFindWasherViewModel by viewModels<OwnerFindWasherViewModel>()
    private val findAdapter = FindRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }


    private fun initUi() {

        //새로고침 구현
        with(binding) {
            pullToRefresh.setOnRefreshListener {
                pullToRefresh.isRefreshing = false
                findAdapter.addAll(mockList)
            }
            findWasherRecycler.adapter = findAdapter
        }

        //초기 리사이클러뷰 구현.
        with(findAdapter) {
            addAll(mockList)
            setItemClickListener { item, clickType ->
                when (clickType) {
                    is ClickType.Expand -> {
                        toggleExpand(item)
                    }

                    is ClickType.Route -> {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.container_owner_find_washer, OrderStatusOwnerFragment())
                            .addToBackStack("").commit()
                    }
                }
            }
        }
    }


    companion object {

        private val mockData = WasherInfo( //리사이클러뷰 초기값 설정
            name = "홍길동",
            count = 5,
            point = 90,
            deliPrice = 6000,
            policyPrice = 20000,
            location = "압구정동 3동"
        )

        private val mockList = mutableListOf<WasherInfo>().apply {
            for (i in 0..100) {
                add(mockData.copy(name = "홍길동 $i"))
            }
        }

    }
}