package dev.rajesh.mobile_banking.dashboard.presentation

import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute

data class DashboardScreenState(
    val currentScreen: DashboardRoute = DashboardRoute.HomeRoute,
    val screens: List<DashboardScreen> = DashboardScreens.dashboardScreens
)