package com.mod_int.carwash.ui.pickup_member

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPmBlankBinding

class PmBlankFragment : BaseFragment<FragmentPmBlankBinding>(
    R.layout.fragment_pm_blank) {

    private lateinit var pmActivity: PmActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PmActivity) pmActivity = context

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val captureLayout = binding.pictureImg as ImageView


    }
}