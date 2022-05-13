package com.mod_int.carwash.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mod_int.carwash.databinding.FragmentWmPriceDialogBinding
import com.mod_int.carwash.manage.findwasher.OmFindWasherViewModel
import com.mod_int.carwash.manage.findwasher.adapter.FindRecyclerAdapter
import com.mod_int.carwash.model.PriceItem
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmPriceDialogFragment : DialogFragment() {
    private val omFindWasherViewModel by activityViewModels<OmFindWasherViewModel>()
    lateinit var binding: FragmentWmPriceDialogBinding
    lateinit var omActivity: OmActivity
    var noBtn: String? = null
    var listener: WmPriceDialogListener? = null

    lateinit var item: PriceItem

    class WmPriceDialogBuilder {
        private val dialog = WmPriceDialogFragment()

        fun create(): WmPriceDialogFragment {
            return dialog
        }

        fun setNoBtn(noBtn: String): WmPriceDialogBuilder {
            dialog.noBtn = noBtn
            return this
        }

        fun setBtnClickListener(listener: WmPriceDialogListener): WmPriceDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun setPriceItem(item: PriceItem): WmPriceDialogBuilder {
            dialog.item = item
            return this
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OmActivity) omActivity = context
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWmPriceDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        isCancelable = false


        var count1 = item.addCost.toInt()

        binding.inOutsideAddCost.text = "${count1+count1}"

        Log.d("값", "${count1+count1}")


        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(binding) {
            priceItem = item
            btnBack.text = noBtn
            btnBack.setOnClickListener {
                listener?.onClickNegativeBtn()
                dismiss()
            }
            return view
        }
    }
}

interface WmPriceDialogListener {
    fun onClickNegativeBtn()
}
