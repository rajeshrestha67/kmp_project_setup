//package org.rajesh.mobile_banking.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.State
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.remember
//import androidx.navigation.NavController
//import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute
//
//@Composable
//fun NavController.shouldShowBottomBar(): State<Boolean> {
//    val bottomBarVisibleRoutes = remember {
//        listOf(
//            DashboardRoute.HomeRoute.route,
//            DashboardRoute.BankingRoute.route,
//            DashboardRoute.TransactionHistoryRoute.route,
//            DashboardRoute.MenuRoute.route
//        )
//    }
//    return currentBackStackEntryFlow.collectAsState(initial = null).let {
//        remember {
//            derivedStateOf {
//                it.value?.destination?.route in bottomBarVisibleRoutes
//            }
//        }
//    }
//}
