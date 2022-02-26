package com.mod_int.carwash.ui.washer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentBlankWasherBinding

class BlankWasherFragment : BaseFragment<FragmentBlankWasherBinding>(R.layout.fragment_blank_washer) {

    lateinit var washerActivity: WasherActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WasherActivity) washerActivity = context
    }
}