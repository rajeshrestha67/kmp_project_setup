package dev.rajesh.mobile_banking.dashboard.presentation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface DashboardRoute {
    val route: String

    @Serializable
    data object HomeRoute : DashboardRoute {
        override val route = "home"
    }

    @Serializable
    data object BankingRoute : DashboardRoute {

        override val route = "banking"
    }

    @Serializable
    data object TransactionHistoryRoute : DashboardRoute {

        override val route = "transaction_history"
    }

    @Serializable
    data object MenuRoute : DashboardRoute {

        override val route = "menu"
    }

}