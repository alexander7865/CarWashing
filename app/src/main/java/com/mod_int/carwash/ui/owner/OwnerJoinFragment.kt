package com.mod_int.carwash.ui.owner

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOwnerJoinBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class OwnerJoinFragment : BaseFragment<FragmentOwnerJoinBinding>(R.layout.fragment_owner_join),
    View.OnClickListener {

    lateinit var ownerActivity: OwnerActivity
    private var fireStore: FirebaseFirestore? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fireStore = FirebaseFirestore.getInstance()
        brandSelect()
        modelSelect()


        //버튼 가지고와서 클릭리스너 구현
        with(binding) {
            btnCancelRegistration.setOnClickListener(this@OwnerJoinFragment)
            btnCancelJoin.setOnClickListener(this@OwnerJoinFragment)
            btnRegistrationJoin.setOnClickListener(this@OwnerJoinFragment)
        }
    }

    //버튼 클릭 구현
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_cancel_registration -> {
                ownerActivity.backStep()
            }

            R.id.btn_cancel_join -> {

            }
            R.id.btn_registration_join -> {
                saveInfoOwner()

            }
        }
    }


    private fun saveInfoOwner(){
        val ownerInfo = OwnerInfo(
            CarNumber = binding.etCarNum.text.toString(),
            CarBrand = binding.etCarBrand.toString(),
            CarModel = binding.etCarModel.toString(),
            CarColor = binding.etCarCol.text.toString(),
            CarLocation = binding.tvCarLocation.text.toString(),
        )
        CoroutineScope(Dispatchers.Main).launch {
            val etCarNumInputCheck = async { etCarNumInputCheck() }
            val etCarBrandInputCheck = async { etCarBrandInputCheck() }
            val etCarModelInputCheck = async { etCarModelInputCheck() }
            val etCarColInputCheck = async { etCarColInputCheck() }
            if (etCarNumInputCheck.await() && etCarBrandInputCheck.await() && etCarModelInputCheck.await() && etCarColInputCheck.await()) {
                val user = Firebase.auth.currentUser
                user?.let {
                    val email = user.email
                    //저장될 위치 OwnerInfo -> SetOptions.merge() 덮어씌기 방지
                    fireStore?.collection("ownerMember")?.document(
                        "$email"
                    )?.set(ownerInfo, SetOptions.merge())?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            enableSetting(false)
                            val toastCenter =
                                Toast.makeText(ownerActivity, "정보가 저장되었습니다", Toast.LENGTH_SHORT)
                            toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                            toastCenter.show()
                        } else {
                            val toastCenter =
                                Toast.makeText(ownerActivity, "정보가 저장되지 않았습니다", Toast.LENGTH_SHORT)
                            toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                            toastCenter.show()
                        }
                    }
                }
            }else{
                val toastCenter =
                    Toast.makeText(ownerActivity, "정보를 모두 입력하세요", Toast.LENGTH_SHORT)
                toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                toastCenter.show()

            }
        }
    }




    private fun etCarNumInputCheck() : Boolean {
        val etCarNum = binding.etCarNum.text.toString()
        return when{
            etCarNum.isEmpty() -> {
                false
            }
            else -> true
        }
    }

    private fun etCarBrandInputCheck() : Boolean {
        val etCarBrand = binding.etCarBrand.toString()
        return when{
            etCarBrand.isEmpty() -> {
                false
            }
            else -> true
        }
    }
    private fun etCarModelInputCheck() : Boolean {
        val etCarModel = binding.etCarModel.toString()
        return when{
            etCarModel.isEmpty() -> {
                false
            }
            else -> true
        }
    }
//    private fun etCarKindsInputCheck() : Boolean {
//        val etCarKinds = binding.etCarKinds.toString()
//        return when{
//            etCarKinds.isEmpty() -> {
//                false
//            }
//            else -> true
//        }
//    }
//    private fun etCarSizeInputCheck() : Boolean {
//        val etCarSize = binding.etCarSize.text.toString()
//        return when{
//            etCarSize.isEmpty() -> {
//                false
//            }
//            else -> true
//        }
//    }
    private fun etCarColInputCheck() : Boolean {
        val etCarCol = binding.etCarCol.text.toString()
        return when{
            etCarCol.isEmpty() -> {
                false
            }
            else -> true
        }
    }


    private fun enableSetting(isEnable: Boolean) {
        with(binding) {
            etCarNum.isEnabled = isEnable
            etCarBrand.isEnabled = isEnable
            etCarModel.isEnabled = isEnable
//            etCarKinds.isEnabled = isEnable
//            etCarSize.isEnabled = isEnable
            etCarCol.isEnabled = isEnable
        }
    }

    data class OwnerInfo (
        var CarNumber : String = "",
        var CarBrand : String = "",
        var CarModel : String = "",
//        var CarKinds : String = "",
//        var CarSize : String = "",
        var CarColor : String = "",
        var CarLocation : String = ""

    )

    //브랜드별 차량 종류를 구현하려 하는데 사실 무식하게 하면 할 수 있을꺼 같은데 더 좋은 방법이 있을까요? ㅋㅋㅋ
    //관리자 페이지 만들어서 새로운 모델 변경된 모델이 나오면 변경해주는 방식으로 진행하려합니다

    private fun brandSelect() {
        val brand = resources.getStringArray(R.array.carBrandSelect)
        val brandAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_owner_spinner, brand)

        with(binding){
            etCarBrand.adapter = brandAdapter
            etCarBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

    //스피너
    private fun modelSelect() {
        val model = resources.getStringArray(R.array.carModelSelect)
        val modelAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_owner_spinner, model)

        with(binding){
            etCarModel.adapter = modelAdapter
            etCarModel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
