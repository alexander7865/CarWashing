package com.mod_int.carwash.ui.washer

import android.content.Context
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWasherBlankBinding

class WasherBlankFragment : BaseFragment<FragmentWasherBlankBinding>(R.layout.fragment_washer_blank) {

    lateinit var washerActivity: WasherActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WasherActivity) washerActivity = context
    }
}