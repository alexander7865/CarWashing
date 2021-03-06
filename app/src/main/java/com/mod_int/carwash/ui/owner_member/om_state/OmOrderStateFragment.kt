package com.mod_int.carwash.ui.owner_member.om_state

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmOrderStatusBinding
import com.mod_int.carwash.ext.showSpinner
import com.mod_int.carwash.model.PriceItem
import com.mod_int.carwash.ui.blank.OmBlankFragment
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.ui.dialog.CustomDialogListener
import com.mod_int.carwash.ui.dialog.WmPriceDialogViewModel
import com.mod_int.carwash.ui.dialog.order.CustomDialogOrderViewModel
import com.mod_int.carwash.ui.dialog.order.CustomDialogOrderViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmOrderStateFragment : BaseFragment<FragmentOmOrderStatusBinding>(
    R.layout.fragment_om_order_status
) {
    private val omOrderStateViewModel by viewModels<OmOrderStateViewModel>()

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
        omOrderStateViewModel.orderStateInfo()

    }

    private fun initUi() {
        washingPointSelect()
        pickupPointSelect()
        setFragmentResultListener("request"){ _, bundle ->
            val result = bundle.getString("senderKey")
            omOrderStateViewModel.companyName.set("$result")
            if(omOrderStateViewModel.companyName.get()?.isNotEmpty() == true ) {
                omOrderStateViewModel.wmBankInfo()

            }
        }
    }

    private fun initViewModel() {
        binding.viewModel = omOrderStateViewModel
        omOrderStateViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OmOrderStateViewState)?.let {
                onChangedOrderSateViewState(viewState)
            }
        }
    }


    private fun onChangedOrderSateViewState(viewState: OmOrderStateViewState) {
        when (viewState) {
            is OmOrderStateViewState.RouteHistory -> {
                pickupCfmDialog()
            }
        }
    }



    private fun pickupCfmDialog() {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("????????? ???????????????????")
            .setQuestion("????????? ?????????????????? '??????' ????????? ??????????????????! ?????? ????????? [????????????] ??????\n?????? ??? ??? ????????????.")
            .setNoBtn("????????? ??????")
            .setYesBtn("????????????")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {
                    //?????????????????? ??????
                }

                override fun onClickPositiveBtn() {
                    routeOmBlackFragment()
                }
            }).create()
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }

    private fun routeOmBlackFragment() {
        parentFragmentManager.beginTransaction().add(
            R.id.container_om_order_state,
            OmBlankFragment()
        ).addToBackStack("OmBlankFragment").commit()
    }

    //????????? ????????????
    private fun washingPointSelect() {
        showSpinner(
            binding.washingPoint,
            resources.getStringArray(R.array.washingPickupPointSelect)
        ).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    //????????? ????????????
    private fun pickupPointSelect() {
        showSpinner(
            binding.pickupPoint,
            resources.getStringArray(R.array.washingPickupPointSelect)
        ).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    //???????????? ???????????? ?????? ?????? ?????? ???????????? ??????????????? ?????? ??????
    private fun washerMemberPhoneNr() {
        var intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("")
        startActivity(intent)

    }
}