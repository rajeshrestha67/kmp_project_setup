package dev.rajesh.mobile_banking.dashboard.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.rajesh.mobile_banking.components.AnimatedNavHost
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.navigationBar.NavigationBar
import dev.rajesh.mobile_banking.dashboard.presentation.DashboardScreenAction
import dev.rajesh.mobile_banking.dashboard.presentation.DashboardScreenState
import dev.rajesh.mobile_banking.dashboard.presentation.DashboardViewModel
import dev.rajesh.mobile_banking.dashboard.navigation.bankingScreenNavGraph
import dev.rajesh.mobile_banking.dashboard.navigation.homeScreenNavGraph
import dev.rajesh.mobile_banking.dashboard.navigation.menuScreennavGraph
import dev.rajesh.mobile_banking.dashboard.navigation.transactionHistoryNavGraph
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute
import dev.rajesh.mobile_banking.logger.AppLogger
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen() {
    val dashboardViewModel: DashboardViewModel = koinViewModel()
    val state by dashboardViewModel.state.collectAsStateWithLifecycle()

    DashboardScreenContent(
        state = state,
        onAction = dashboardViewModel::action,
    )
}

@Composable
fun DashboardScreenContent(
    state: DashboardScreenState,
    onAction: (DashboardScreenAction) -> Unit,
) {
    var bottomBarState by remember { mutableStateOf(true) }
    val dashboardNavController = rememberNavController()



    LaunchedEffect(dashboardNavController) {
        dashboardNavController.addOnDestinationChangedListener { _, destination, _ ->
            AppLogger.i("Dashboard", "destination qualifiedName: ${destination.route}")
            bottomBarState = when (destination.route?.substringBefore('?')) {
                DashboardRoute.HomeRoute::class.qualifiedName,
                DashboardRoute.BankingRoute::class.qualifiedName,
                DashboardRoute.TransactionHistoryRoute::class.qualifiedName,
                DashboardRoute.MenuRoute::class.qualifiedName -> {
                    true
                }

                else -> {
                    false
                }
            }

        }
    }

    val navBackStackEntry by dashboardNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        if (currentRoute != null) {
            val destination = when (currentRoute.substringBefore('?')) {
                DashboardRoute.HomeRoute::class.qualifiedName -> {
                    DashboardRoute.HomeRoute
                }

                DashboardRoute.BankingRoute::class.qualifiedName -> {
                    DashboardRoute.BankingRoute
                }

                DashboardRoute.TransactionHistoryRoute::class.qualifiedName -> {
                    DashboardRoute.TransactionHistoryRoute
                }

                DashboardRoute.MenuRoute::class.qualifiedName -> {
                    DashboardRoute.MenuRoute
                }

                else -> {
                    DashboardRoute.HomeRoute
                }
            }
            onAction(DashboardScreenAction.OnChangeScreen(destination))
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.appColors.backgroundColor,
        bottomBar = {
            AnimatedContent(
                targetState = bottomBarState, transitionSpec = {
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(durationMillis = 200)
                    ) togetherWith slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(durationMillis = 200)
                    )
                }) { visible ->
                if (visible) {
                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        contentColor = MaterialTheme.appColors.backgroundColor,
                        containerColor = MaterialTheme.appColors.navigationBarBackgroundColor
                    ) {
                        state.screens.forEach { item ->
                            NavigationBarItem(
                                selected = item.route == state.currentScreen,
                                onClick = {
                                    if (item.route == state.currentScreen) return@NavigationBarItem
                                    onAction(
                                        DashboardScreenAction.OnChangeScreen(item.route)
                                    )
                                    dashboardNavController.navigate(item.route) {
                                        popUpTo(DashboardRoute.HomeRoute) {
                                            inclusive = false
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = stringResource(item.name),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.appColors.primaryColor,
                                    selectedTextColor = MaterialTheme.appColors.primaryColor,
                                    indicatorColor = MaterialTheme.appColors.primaryContainerColor,
                                    unselectedIconColor = Color.Gray,
                                    unselectedTextColor = Color.Gray
                                )
                            )
                        }
                    }
                }

            }
        }
    ) { contentPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding)
                .animateContentSize()
        ) {
            AnimatedNavHost(
                modifier = Modifier.fillMaxSize(),
                navController = dashboardNavController,
                startDestination = DashboardRoute.HomeRoute,
            ) {
                homeScreenNavGraph(navController = dashboardNavController,
                    onExitToDashboard = {
                        dashboardNavController.popBackStack(
                            route = DashboardRoute.HomeRoute,
                            inclusive = false
                        )
                    }
                )
                bankingScreenNavGraph(navController = dashboardNavController)
                transactionHistoryNavGraph(navController = dashboardNavController)
                menuScreennavGraph(navController = dashboardNavController)
            }
        }

    }
}