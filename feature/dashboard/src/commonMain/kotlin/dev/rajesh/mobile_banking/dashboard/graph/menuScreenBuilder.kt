package dev.rajesh.mobile_banking.dashboard.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.dashboard.route.DashboardRoute
import dev.rajesh.mobile_banking.menu.MenuScreen


fun NavGraphBuilder.menuScreenBuilder(
    navController: NavController
) {
    composable<DashboardRoute.MenuRoute> {
        MenuScreen()
    }
}