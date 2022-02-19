package com.mod_int.carwash.ui.owner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.mod_int.carwash.databinding.FragmentOrderStatusOwnerBinding

class OrderStatusOwnerFragment : Fragment() {

    lateinit var binding: FragmentOrderStatusOwnerBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderStatusOwnerBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvPhoneNumberOwner.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:821082277865")
            startActivity(intent)
        }

        setFragmentResultListener("request") { key, bundle ->
            bundle.getString("sender")?.let{ value ->
                binding.tvText.text = value
            }
        }
    }
}