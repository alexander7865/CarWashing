package com.mod_int.carwash.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityRegisterBinding
import com.mod_int.carwash.ui.owner_member.OmActivity
import com.mod_int.carwash.ui.pickup_member.PmActivity
import com.mod_int.carwash.ui.washer_member.WmActivity
import dagger.hilt.android.AndroidEntryPoint

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

            is RegisterViewState.RouteOwnerMember -> {
                startActivity(Intent(this@RegisterActivity, OmActivity::class.java))
            }

            is RegisterViewState.RouteWasherMember -> {
                startActivity(Intent(this@RegisterActivity, WmActivity::class.java))
            }

            is RegisterViewState.RoutePickupMember -> {
                startActivity(Intent(this@RegisterActivity, PmActivity::class.java))
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