package org.rajesh.mobile_banking.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.login.LoginScreen
import org.rajesh.mobile_banking.route.AppRoute

fun NavGraphBuilder.loginScreenBuilder(navController: NavController) {

    composable<AppRoute.LoginRoute> {
        LoginScreen(onNavigateToDashboard = {
            navController.navigate(AppRoute.DashboardRoute) {
                popUpTo(AppRoute.LoginRoute) { inclusive = true }
            }
        })
    }
}