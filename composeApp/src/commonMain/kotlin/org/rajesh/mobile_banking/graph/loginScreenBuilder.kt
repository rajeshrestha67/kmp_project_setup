package org.rajesh.mobile_banking.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.login.presentation.ui.LoginScreen
import dev.rajesh.mobile_banking.otpverification.presentation.ui.OtpVerificationScreen
import org.rajesh.mobile_banking.route.AppRoute

fun NavGraphBuilder.loginScreenBuilder(navController: NavHostController) {

    composable<AppRoute.LoginRoute> {
        LoginScreen(
            onNavigateToDashboard = {
                navController.navigate(AppRoute.DashboardRoute) {
                    popUpTo(AppRoute.LoginRoute) { inclusive = true }
                }
            },
            onNavigateToOtpVerification = {
                navController.navigate(AppRoute.OtpVerificationRoute)
            }
        )
    }

    composable<AppRoute.OtpVerificationRoute> {
        OtpVerificationScreen(onBackClicked = {
            navController.popBackStack()
        })
    }
}