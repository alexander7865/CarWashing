package com.mod_int.carwash.ui.owner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentBlankOwnerBinding

class BlankOwnerFragment : BaseFragment<FragmentBlankOwnerBinding>(R.layout.fragment_blank_owner) {

    lateinit var ownerActivity: OwnerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
    }
}