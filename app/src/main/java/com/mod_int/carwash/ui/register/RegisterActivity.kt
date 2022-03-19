package com.mod_int.carwash.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityRegisterBinding
import com.mod_int.carwash.ui.login.LoginViewState
import com.mod_int.carwash.ui.owner.OwnerActivity
import com.mod_int.carwash.ui.pickup_manager.PickupManagerActivity
import com.mod_int.carwash.ui.washer.WasherActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {


    private val registerViewModel by viewModels<RegisterViewModel>()


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    private fun initViewModel() {
        binding.viewModel = registerViewModel
        Log.d("결과", intent.getStringExtra(KEY_TYPE).toString())

        registerViewModel.typeObservableField.set(intent.getStringExtra(KEY_TYPE))
        registerViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? RegisterViewState)?.let {
                onChangedRegisterViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedRegisterViewState(viewState: RegisterViewState) {
        when (viewState) {
            is RegisterViewState.Error -> {
                showToast(message = viewState.message)
            }

            is RegisterViewState.Cancel -> {
                onBackPressed()
                overridePendingTransition(0, 0) //애니메이션 효과없에기
            }

            is RegisterViewState.EnableInput -> {
                enableSetting(viewState.isEnable)
            }

            is RegisterViewState.RouteOwner -> {
                startActivity(Intent(this@RegisterActivity, OwnerActivity::class.java))
            }

            is RegisterViewState.RouteWasher -> {
                startActivity(Intent(this@RegisterActivity, WasherActivity::class.java))
            }

            is RegisterViewState.RoutePickupManager -> {
                startActivity(Intent(this@RegisterActivity, PickupManagerActivity::class.java))
            }
        }
    }

    private fun enableSetting(isEnable: Boolean) {
        with(binding) {
            inputEmailRegister.isEnabled = isEnable
            inputPassRegister.isEnabled = isEnable
            inputCfmPassRegister.isEnabled = isEnable
        }
    }

    data class User(
        var email: String = "",
        var phoneNumber: String = "",
        var type: String = "",
    )

    companion object {
        const val KEY_TYPE = "key_type"
    }
}


fun AppCompatActivity.showToast(message: String) {
    val toastCenter = Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    )
    toastCenter.setGravity(
        Gravity.CENTER,
        Gravity.CENTER_HORIZONTAL,
        0
    )
    toastCenter.show()
}