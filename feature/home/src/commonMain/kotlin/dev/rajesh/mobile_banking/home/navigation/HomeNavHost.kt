package dev.rajesh.mobile_banking.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dev.rajesh.mobile_banking.components.AnimatedNavHost

@Composable
fun HomeNavHost(
    onExitHome: () -> Unit = {}
) {
    val homeNavController = rememberNavController()

    AnimatedNavHost(
        navController = homeNavController,
        startDestination = HomeRoute.Home
    ) {
        homeNavGraph(
            navController = homeNavController,
           // onExitHome = onExitHome
        )
    }
}