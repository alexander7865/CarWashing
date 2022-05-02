package com.mod_int.carwash.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mod_int.carwash.databinding.FragmentCustomDialogBinding
import com.mod_int.carwash.ui.washer_member.wm_activity.WmActivity

class CustomDialogFragment : DialogFragment() {
    lateinit var binding: FragmentCustomDialogBinding
    lateinit var wmActivity: WmActivity
    var title : String? = null
    var question : String? = null
    var noBtn : String? = null
    var yesBtn : String? = null
    var listener: CustomDialogListener? = null

    class CustomDialogBuilder {
        private val dialog = CustomDialogFragment()

        fun setTitle(title: String): CustomDialogBuilder {
            dialog.title = title
            return this
        }
        fun setQuestion(question: String): CustomDialogBuilder {
            dialog.question = question
            return this
        }
        fun setNoBtn(noBtn: String): CustomDialogBuilder {
            dialog.noBtn = noBtn
            return this
        }
        fun setYesBtn(yesBtn: String): CustomDialogBuilder {
            dialog.yesBtn = yesBtn
            return this
        }
        fun setBtnClickListener(listener: CustomDialogListener): CustomDialogBuilder {
            dialog.listener = listener
            return this
        }
        fun create(): CustomDialogFragment {
            return dialog
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WmActivity) wmActivity = context
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { binding = FragmentCustomDialogBinding.inflate(inflater,container,false)
        val view = binding.root
        isCancelable = false

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(binding){
            titleDialog.text = title
            tvQuestion.text = question
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

}

interface CustomDialogListener {
    fun onClickNegativeBtn()
    fun onClickPositiveBtn()

}
