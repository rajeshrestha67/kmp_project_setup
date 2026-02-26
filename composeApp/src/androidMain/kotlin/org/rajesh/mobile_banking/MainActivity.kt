package org.rajesh.mobile_banking

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mmk.kmpnotifier.extensions.onCreateOrOnNewIntent
import com.mmk.kmpnotifier.notification.NotifierManager
import dev.rajesh.mobile_banking.splashscreen.presentation.state.OnBoardingScreenAction
import dev.rajesh.mobile_banking.splashscreen.viewModel.OnBoardingViewModel
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {

    var showSplashScreen by mutableStateOf(true)
    var navigateToOnBoarding by mutableStateOf(true)


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        NotifierManager.onCreateOrOnNewIntent(intent)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !showSplashScreen
            }
        }

        setContent {
            val viewModel: OnBoardingViewModel = koinViewModel()
            LaunchedEffect(Unit) {
                viewModel.action(OnBoardingScreenAction.HasShownOnBoarding)
            }

            LaunchedEffect(Unit) {
                viewModel.navigationChannel.collect { hasShownOnBoarding ->
                    showSplashScreen = true
                    navigateToOnBoarding = hasShownOnBoarding
                }
            }

            App(
                hasShownOnBoarding = navigateToOnBoarding
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        NotifierManager.onCreateOrOnNewIntent(intent)
    }
}
