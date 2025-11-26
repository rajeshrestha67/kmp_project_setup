package dev.rajesh.mobile_banking.dashboard.presentation

import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute

sealed interface DashboardScreenAction {
    data class OnChangeScreen(val route: DashboardRoute) : DashboardScreenAction
}