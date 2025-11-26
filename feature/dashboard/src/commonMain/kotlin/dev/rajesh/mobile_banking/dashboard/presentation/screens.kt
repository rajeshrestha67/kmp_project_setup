package dev.rajesh.mobile_banking.dashboard.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute
import dev.rajesh.mobile_banking.res.SharedRes
import org.jetbrains.compose.resources.StringResource

data class DashboardScreen(
    val name: StringResource,
    val icon: ImageVector,
    val route: DashboardRoute
)

object DashboardScreens {
    val dashboardScreens = listOf(
        DashboardScreen(
            SharedRes.Strings.home,
            Icons.Filled.Home,
            DashboardRoute.HomeRoute
        ),
        DashboardScreen(
            SharedRes.Strings.banking,
            Icons.Filled.FoodBank,
            DashboardRoute.BankingRoute
        ),
        DashboardScreen(
            SharedRes.Strings.transactionHistory,
            Icons.Filled.History,
            DashboardRoute.TransactionHistoryRoute
        ),
        DashboardScreen(
            SharedRes.Strings.menu,
            Icons.Filled.Menu,
            DashboardRoute.MenuRoute
        )
    )
}