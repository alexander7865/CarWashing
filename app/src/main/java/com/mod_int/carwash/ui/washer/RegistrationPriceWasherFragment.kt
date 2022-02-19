package com.mod_int.carwash.ui.washer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mod_int.carwash.MainViewModel
import com.mod_int.carwash.databinding.FragmentRegistrationPriceWasherBinding
import java.nio.channels.Selector

class RegistrationPriceWasherFragment : Fragment() {

    lateinit var binding: FragmentRegistrationPriceWasherBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationPriceWasherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}