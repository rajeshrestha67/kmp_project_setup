package org.rajesh.mobile_banking.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.splashscreen.presentation.ui.OnBoardingScreen
import org.rajesh.mobile_banking.route.AppRoute

fun NavGraphBuilder.onBoardingBuilder(navController: NavHostController) {
    composable<AppRoute.OnBoardingRoute> {
        OnBoardingScreen(onNavigateToLogin = {
            navController.navigate(AppRoute.LoginRoute) {
                popUpTo(AppRoute.OnBoardingRoute) { inclusive = true }
            }
        })
    }
}