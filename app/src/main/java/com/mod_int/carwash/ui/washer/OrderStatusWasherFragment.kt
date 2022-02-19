package com.mod_int.carwash.ui.washer

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import androidx.fragment.app.setFragmentResult
import com.mod_int.carwash.databinding.FragmentOrderStatusWasherBinding
import java.util.*

class OrderStatusWasherFragment : Fragment() {

    lateinit var binding: FragmentOrderStatusWasherBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderStatusWasherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPhoneNumber.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:821082277865")
            startActivity(intent)
        }




        with(binding) {
            btnCfm1.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                val getTime = Date()
                val formatType = SimpleDateFormat("HH:mm:ss")

                builder.setTitle("세차 작업이 가능합니까?")
                builder.setCancelable(false)
                builder.setMessage("가능하다면 신속히 차량의 위치로 이동 하여 차주와 연락을 취해 해주세요!")
                builder.setPositiveButton("네") {
                        dialogInterface: DialogInterface, i: Int ->
                    with(binding){
//                        btnPickupCfm.visibility = View.VISIBLE
//                        pickupTime1.visibility = View.VISIBLE
//                        tvQuestion1.visibility = View.VISIBLE
                        btnCfm1.isEnabled = false
                        btnCfm1.setBackgroundColor(Color.TRANSPARENT)
                        btnCfm1.setTextColor(Color.GRAY)
                        btnCfm1.setTextSize(5,2.8F)
                        btnCfm1.setText("작업확인")
                        cfmTime.text = formatType.format(getTime)
                        cfmTime.setTextColor(Color.GRAY)
//                        cfmTime.setTextColor(Color.parseColor("#FF8C00"))

                    }
                }

                builder.setNegativeButton("아니요") {
                        dialogInterface: DialogInterface, i: Int ->

                }

                builder.show()

                val bundle = bundleOf("sender" to "오케이굿")
                setFragmentResult("request", bundle)
            }
        }
    }
}