package org.rajesh.mobile_banking.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.dashboard.ui.DashboardScreen
import org.rajesh.mobile_banking.route.AppRoute

fun NavGraphBuilder.dashboardScreenBuilder(navController: NavController) {
    composable<AppRoute.DashboardRoute> {
        DashboardScreen()
    }
}