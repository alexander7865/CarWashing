package com.mod_int.carwash.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityLoginBinding
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity
import com.mod_int.carwash.ui.pickup_member.pm_activity.PmActivity
import com.mod_int.carwash.ui.register.showToast
import com.mod_int.carwash.ui.washer_member.wm_activity.WmActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    btnLoginOwner.isEnabled = viewState.isEnable
                }
            }

            is LoginViewState.RouteOwnerMember -> {
                startActivity(Intent(this@LoginActivity, OmActivity::class.java))
            }

            is LoginViewState.RouteWasherMember -> {
                startActivity(Intent(this@LoginActivity, WmActivity::class.java))
            }

            is LoginViewState.RoutePickupMember -> {
                startActivity(Intent(this@LoginActivity, PmActivity::class.java))
            }

            is LoginViewState.RemoveAnimation -> {
                overridePendingTransition(0, 0) //애니메이션 효과없에기
            }
        }
    }
}