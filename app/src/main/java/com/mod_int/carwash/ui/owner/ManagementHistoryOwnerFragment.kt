package com.mod_int.carwash.ui.owner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentManagementHistoryOwnerBinding
import com.mod_int.carwash.history_recycler_adapter.HistoryInfo
import com.mod_int.carwash.history_recycler_adapter.HistoryRecyclerAdapter

class ManagementHistoryOwnerFragment : BaseFragment<FragmentManagementHistoryOwnerBinding>(
    com.mod_int.carwash.R.layout.fragment_management_history_owner){

    private val historyAdapter = HistoryRecyclerAdapter()
    private val historyRecycler : RecyclerView by lazy {binding.recyclerHistory}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRecycler.run {
            adapter = historyAdapter
        }

        val historyData = HistoryInfo(
            historyDate = "2022.02.02",
            washTypeCategory = "내부/외부 세차(외제차)",
            carNumber = "123허1234",
            brandHistory = "벤츠",
            styleNameHistory = "GLC220",
            carKindsHistory = "SUV",
            carColorHistory = "BLACK",
        )

        val historyList = mutableListOf<HistoryInfo>().apply {
            for (i in 0..2) {
                add(historyData)
            }
        }
        historyAdapter.addAll(historyList)
        historyAdapter.setItemClickListener { item ->

        }

        binding.btnCancel.setOnClickListener {

        }
    }
}