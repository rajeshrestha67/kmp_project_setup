package dev.rajesh.mobile_banking.dashboard.model

import dev.rajesh.mobile_banking.dashboard.route.DashboardRoute

sealed interface DashboardScreenAction {
    data class OnChangeScreen(val route: DashboardRoute) : DashboardScreenAction
}