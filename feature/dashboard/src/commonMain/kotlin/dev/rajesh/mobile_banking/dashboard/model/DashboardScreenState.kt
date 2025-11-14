package dev.rajesh.mobile_banking.dashboard.model

import dev.rajesh.mobile_banking.dashboard.route.DashboardRoute

data class DashboardScreenState(
    val currentScreen: DashboardRoute = DashboardRoute.HomeRoute,
    val screens: List<DashboardScreen> = DashboardScreens.dashboardScreens
)