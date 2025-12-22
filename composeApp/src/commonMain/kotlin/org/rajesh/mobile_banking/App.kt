package org.rajesh.mobile_banking

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.rajesh.mobile_banking.components.AnimatedNavHost
import dev.rajesh.mobile_banking.components.AppTheme
import dev.rajesh.mobile_banking.res.theme.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.rajesh.mobile_banking.graph.dashboardScreenBuilder
import org.rajesh.mobile_banking.graph.loginScreenBuilder
import org.rajesh.mobile_banking.route.AppRoute

@Composable
@Preview
fun App() {
    val theme = ThemeMode.get(2)
    AppTheme(selectedThemeMode = theme) {
        AppScreen()
    }

}

@Composable
fun AppScreen() {
    val navController = rememberNavController()

    AnimatedNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = AppRoute.LoginRoute
    ) {
        loginScreenBuilder(navController)
        dashboardScreenBuilder(navController)
    }
}