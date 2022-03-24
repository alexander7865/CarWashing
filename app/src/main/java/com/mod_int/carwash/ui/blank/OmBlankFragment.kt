package com.mod_int.carwash.ui.blank

import android.content.Context
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmBlankBinding
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity

class OmBlankFragment : BaseFragment<FragmentOmBlankBinding>(R.layout.fragment_om_blank) {

    lateinit var ownerActivity: OmActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OmActivity) ownerActivity = context
    }
}