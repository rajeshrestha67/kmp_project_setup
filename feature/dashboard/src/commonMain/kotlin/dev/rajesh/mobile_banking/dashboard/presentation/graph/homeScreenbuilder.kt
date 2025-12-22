package dev.rajesh.mobile_banking.dashboard.presentation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute
import dev.rajesh.mobile_banking.home.HomeScreen

fun NavGraphBuilder.homeScreenBuilder(
    navController: NavController
) {
    composable<DashboardRoute.HomeRoute> {
        HomeScreen(navController)
    }
}