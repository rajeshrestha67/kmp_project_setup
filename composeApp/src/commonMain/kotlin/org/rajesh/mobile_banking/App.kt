package org.rajesh.mobile_banking

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mmk.kmpnotifier.notification.Notifier
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import dev.rajesh.mobile_banking.components.AnimatedNavHost
import dev.rajesh.mobile_banking.components.AppTheme
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.notification.PushNotificationViewModel
import dev.rajesh.mobile_banking.res.theme.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
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
    val pushNotificationViewModel: PushNotificationViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onNewToken(token: String) {
                //super.onNewToken(token)
                AppLogger.i("App", "PushToken : $token")
                pushNotificationViewModel.register()
            }

            override fun onNotificationClicked(data: PayloadData) {
                //super.onNotificationClicked(data)
                AppLogger.i("App", "Notification payload: $data")
            }
        })
    }

    AnimatedNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = AppRoute.LoginRoute
    ) {
        loginScreenBuilder(navController)
        dashboardScreenBuilder(navController)
    }
}