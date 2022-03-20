package com.mod_int.carwash.ui.washer_member

import android.content.Context
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmBlankBinding

class WmBlankFragment : BaseFragment<FragmentWmBlankBinding>(R.layout.fragment_wm_blank) {

    lateinit var wmActivity: WmActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WmActivity) wmActivity = context
    }
}