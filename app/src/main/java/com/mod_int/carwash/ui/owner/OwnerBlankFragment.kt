package com.mod_int.carwash.ui.owner

import android.content.Context
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOwnerBlankBinding

class OwnerBlankFragment : BaseFragment<FragmentOwnerBlankBinding>(R.layout.fragment_owner_blank) {

    lateinit var ownerActivity: OwnerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
    }
}