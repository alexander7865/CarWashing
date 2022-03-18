package com.mod_int.carwash

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.mod_int.carwash.databinding.FragmentCustomDialogOrderBinding
import com.mod_int.carwash.ui.owner.OwnerActivity

class CustomDialogOrderFragment : DialogFragment() {
    lateinit var binding: FragmentCustomDialogOrderBinding
    lateinit var ownerActivity: OwnerActivity
    var title1 : String? = null
    var request : String? = null
    var noBtn : String? = null
    var yesBtn : String? = null
    var listener: CustomDialogOrderListener? = null

    class CustomDialogOrderBuilder {
        private val dialog = CustomDialogOrderFragment()

        fun setTitle(title1: String): CustomDialogOrderBuilder {
            dialog.title1 = title1
            return this
        }
        fun setRequest(request: String): CustomDialogOrderBuilder {
            dialog.request = request
            return this
        }
        fun setNoBtn(noBtn: String): CustomDialogOrderBuilder {
            dialog.noBtn = noBtn
            return this
        }
        fun setYesBtn(yesBtn: String): CustomDialogOrderBuilder {
            dialog.yesBtn = yesBtn
            return this
        }
        fun setBtnClickListener(listener: CustomDialogOrderListener): CustomDialogOrderBuilder {
            dialog.listener = listener
            return this
        }
        fun create(): CustomDialogOrderFragment {
            return dialog
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { binding = FragmentCustomDialogOrderBinding.inflate(inflater,container,false)
        val view = binding.root
        isCancelable = false

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(binding){
            timeSelect()
            title1.text = binding.title1.text
            tvRequest.text = binding.tvRequest.text
            tvNo.text = noBtn
            tvNo.setOnClickListener {
                listener?.onClickNegativeBtn()
                dismiss()
            }
            tvYes.text = yesBtn
            tvYes.setOnClickListener {
                listener?.onClickPositiveBtn()
                dismiss()
            }
            return view
        }
    }

    //다이얼로그에 스피너 구현
    private fun timeSelect() {
        val time = resources.getStringArray(R.array.timeSelect)
        val timeAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_spinner, time)

        with(binding){
            timeSelect.adapter = timeAdapter
            timeSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }
}

interface CustomDialogOrderListener {
    fun onClickNegativeBtn()
    fun onClickPositiveBtn()

}
