package com.mod_int.carwash

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mod_int.carwash.databinding.FragmentCustomDialogBinding
import com.mod_int.carwash.ui.washer.WasherActivity

class CustomDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentCustomDialogBinding
    lateinit var washerActivity: WasherActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        isCancelable = false
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = FragmentCustomDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = "안녕하세요 여러분"

        with(binding){
            tvQuestion.text = text

            tvNo.setOnClickListener {
                dismiss()
            }

            tvYes.setOnClickListener {
                dismiss()
            }
        }
    }
}