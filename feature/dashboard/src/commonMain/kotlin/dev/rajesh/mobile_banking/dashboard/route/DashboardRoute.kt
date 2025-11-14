package dev.rajesh.mobile_banking.dashboard.route

import kotlinx.serialization.Serializable

sealed interface DashboardRoute {
    @Serializable
    data object HomeRoute : DashboardRoute

    @Serializable
    data object BankingRoute : DashboardRoute

    @Serializable
    data object TransactionHistoryRoute : DashboardRoute

    @Serializable
    data object MenuRoute : DashboardRoute

}