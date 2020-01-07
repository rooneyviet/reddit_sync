package jp.zuikou.system.redditprojectsample1

import android.annotation.SuppressLint
import jp.zuikou.system.redditprojectsample1.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("Registered")
open abstract class BaseAuthActivity : BaseActivity() {
    private val loginViewModel: LoginViewModel by viewModel()

}