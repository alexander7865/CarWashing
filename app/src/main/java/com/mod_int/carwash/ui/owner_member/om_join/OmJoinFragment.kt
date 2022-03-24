package com.mod_int.carwash.ui.owner_member.om_join

import android.annotation.SuppressLint
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
import com.mod_int.carwash.databinding.FragmentOmJoinBinding
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class OmJoinFragment : BaseFragment<FragmentOmJoinBinding>(R.layout.fragment_om_join),
    View.OnClickListener {

    lateinit var ownerActivity: OmActivity
    private var fireStore: FirebaseFirestore? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OmActivity) ownerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fireStore = FirebaseFirestore.getInstance()
        brandSelect()
        modelSelect()


        //버튼 가지고와서 클릭리스너 구현
        with(binding) {
            btnCancelRegistration.setOnClickListener(this@OmJoinFragment)
            btnCancelJoin.setOnClickListener(this@OmJoinFragment)
            btnRegistrationJoin.setOnClickListener(this@OmJoinFragment)
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
            carNumber = binding.etCarNum.text.toString(),
            carBrand = binding.carBrand.text.toString(),
            carModel = binding.carModel.text.toString(),
            carKinds = binding.tvCarKinds.text.toString(),
            carSize = binding.tvCarSize.text.toString(),
            carColor = binding.etCarCol.text.toString(),
            carLocation = binding.carLocation.text.toString(),
        )
        CoroutineScope(Dispatchers.Main).launch {
            val carNumInputCheck = async { carNumInputCheck() }
            val carBrandInputCheck = async { carBrandInputCheck() }
            val carModelInputCheck = async { carModelInputCheck() }
            val carKindsInputCheck = async { carKindsInputCheck() }
            val carSizeInputCheck = async { carSizeInputCheck() }
            val carLocationCheck = async { carLocationCheck() }
            val carColInputCheck = async { etCarColInputCheck() }

            //주소등등 추가 해야함
            if (carNumInputCheck.await() && carBrandInputCheck.await() && carModelInputCheck.await()
                && carKindsInputCheck.await() && carSizeInputCheck.await()
                && carLocationCheck.await() && carColInputCheck.await()) {
                val user = Firebase.auth.currentUser
                user?.let {
                    val email = user.email
                    fireStore?.collection("OwnerMember")?.document(
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




    private fun carNumInputCheck() : Boolean {
        val etCarNum = binding.etCarNum.text.toString()
        return when{
            etCarNum.isEmpty() -> {
                false
            }
            else -> true
        }
    }

    private fun carBrandInputCheck() : Boolean {
        val spCarBrand = binding.spCarBrand.toString()
        return when{
            spCarBrand.isEmpty() -> {
                false
            }
            else -> true
        }
    }
    private fun carModelInputCheck() : Boolean {
        val spCarModel = binding.spCarModel.toString()
        return when{
            spCarModel.isEmpty() -> {
                false
            }
            else -> true
        }
    }

    private fun carKindsInputCheck() : Boolean {
        val tvCarKinds = binding.tvCarKinds.toString()
        return when{
            tvCarKinds.isEmpty() -> {
                false
            }
            else -> true
        }
    }

    private fun carSizeInputCheck() : Boolean {
        val tvCarSize = binding.tvCarSize.text.toString()
        return when{
            tvCarSize.isEmpty() -> {
                false
            }
            else -> true
        }
    }

    private fun etCarColInputCheck() : Boolean {
        val etCarCol = binding.etCarCol.text.toString()
        return when{
            etCarCol.isEmpty() -> {
                false
            }
            else -> true
        }
    }

    private fun carLocationCheck() : Boolean {
        val carLocation = binding.carLocation.text.toString()
        return when{
            carLocation.isEmpty() -> {
                false
            }
            else -> true
        }
    }




    private fun enableSetting(isEnable: Boolean) {
        with(binding) {
            etCarNum.isEnabled = isEnable
            spCarBrand.isEnabled = isEnable
            spCarModel.isEnabled = isEnable
            tvCarKinds.isEnabled = isEnable
            tvCarSize.isEnabled = isEnable
            etCarCol.isEnabled = isEnable
            carLocation.isEnabled = isEnable
        }
    }

    //브랜드별 차량 종류를 구현하려 하는데 사실 무식하게 하면 할 수 있을꺼 같은데 더 좋은 방법이 있을까요? ㅋㅋㅋ
    //관리자 페이지 만들어서 새로운 모델 변경된 모델이 나오면 변경해주는 방식으로 진행하려합니다dd
    private fun brandSelect() {
        val brand = resources.getStringArray(R.array.carBrandSelect)
        val brandAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_owner_spinner, brand)

        with(binding){
            spCarBrand.adapter = brandAdapter
            spCarBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                @SuppressLint("ResourceAsColor")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        var selected = brand[position]
                        carBrand.text = selected
                        val toastCenter =
                            Toast.makeText(requireContext(), "${brand[position]}가 선택되었습니다",
                                Toast.LENGTH_SHORT)
                        toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                        toastCenter.show()
                    }

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
            spCarModel.adapter = modelAdapter
            spCarModel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        var selected = model[position]
                        carModel.text = selected
                        val toastCenter =
                            Toast.makeText(requireContext(), "${model[position]}가 선택되었습니다",
                                Toast.LENGTH_SHORT)
                        toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                        toastCenter.show()

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    data class OwnerInfo (
        var carNumber : String = "",
        var carBrand : String = "",
        var carModel : String = "",
        var carKinds : String = "",
        var carSize : String = "",
        var carColor : String = "",
        var carLocation : String = ""

    )
}
