package org.rajesh.mobile_banking.graph

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.login.presentation.state.LoginViewModel
import dev.rajesh.mobile_banking.login.presentation.ui.LoginScreen
import dev.rajesh.mobile_banking.otpverification.presentation.state.OtpEffect
import dev.rajesh.mobile_banking.otpverification.presentation.ui.OtpVerificationScreen
import org.koin.compose.viewmodel.koinViewModel
import org.rajesh.mobile_banking.route.AppRoute

fun NavGraphBuilder.loginScreenBuilder(navController: NavHostController) {

    composable<AppRoute.LoginRoute> {
        val loginVM: LoginViewModel = koinViewModel()

        // Listen for direct success or "Needs OTP"
        LaunchedEffect(Unit) {
            loginVM.otpEffect.collect { effect ->
                when (effect) {
                    OtpEffect.ActionSuccess -> {
                        navController.navigate(AppRoute.DashboardRoute) {
                            popUpTo(AppRoute.LoginRoute) { inclusive = true }
                        }
                    }

                    OtpEffect.NeedsVerification -> {
                        navController.navigate(AppRoute.OtpVerificationRoute)
                    }

                    else -> {}
                }
            }
        }

        LoginScreen(loginViewModel = loginVM)
    }

    composable<AppRoute.OtpVerificationRoute> {
        // Reuse the same LoginViewModel instance from the backstack
        val loginEntry = remember(it) { navController.getBackStackEntry(AppRoute.LoginRoute) }
        val loginVM: LoginViewModel = koinViewModel(viewModelStoreOwner = loginEntry)

        OtpVerificationScreen(
            otpEffect = loginVM.otpEffect,
            onVerifyClicked = { otpCode -> loginVM.login(otpCode) },
            onResendClicked = {
                loginVM.login()
            },
            onSuccess = {
                navController.navigate(AppRoute.DashboardRoute) {
                    popUpTo(AppRoute.LoginRoute) { inclusive = true }
                }
            },
            onBackClicked = { navController.popBackStack() }
        )

    }
}