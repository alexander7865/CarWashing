package com.mod_int.carwash.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityLoginBinding
import com.mod_int.carwash.showToast
import com.mod_int.carwash.ui.owner.OwnerActivity
import com.mod_int.carwash.ui.pickup_manager.PickupManagerActivity
import com.mod_int.carwash.ui.washer.WasherActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by viewModels<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        initViewModel()
    }

    private fun initUi() {
        binding.inputPassLogin.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login()
                true
            } else {
                false
            }
        }
    }

    private fun initViewModel() {
        binding.viewModel = loginViewModel
        loginViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? LoginViewState)?.let {
                onChangedLoginViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedLoginViewState(viewState: LoginViewState) {
        when (viewState) {

            is LoginViewState.Error -> {
                showToast(message = viewState.message)
            }

            is LoginViewState.Cancel -> {
                onBackPressed()
                overridePendingTransition(0, 0) //애니메이션 효과없에기
            }

            is LoginViewState.EnableInput -> {
                with(binding) {
                    inputEmailLogin.isEnabled = viewState.isEnable
                    inputPassLogin.isEnabled = viewState.isEnable
                }
            }

            is LoginViewState.RouteOwner -> {
                startActivity(Intent(this@LoginActivity, OwnerActivity::class.java))
            }

            is LoginViewState.RoutePickupManager -> {
                startActivity(Intent(this@LoginActivity, PickupManagerActivity::class.java))
            }

            is LoginViewState.RouteWasher -> {
                startActivity(Intent(this@LoginActivity, WasherActivity::class.java))
            }
        }
    }
}