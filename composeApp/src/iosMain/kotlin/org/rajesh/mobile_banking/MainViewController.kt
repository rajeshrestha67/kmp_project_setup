package org.rajesh.mobile_banking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ComposeUIViewController
import dev.rajesh.mobile_banking.splashscreen.presentation.state.OnBoardingScreenAction
import dev.rajesh.mobile_banking.splashscreen.viewModel.OnBoardingViewModel
import org.koin.compose.viewmodel.koinViewModel

fun MainViewController() = ComposeUIViewController {
    remember(Unit) {
        installIosInstitutionBrandingFromMainBundle()
        true
    }
    var showSplashScreen by remember { mutableStateOf(true) }
    var navigateToOnBoarding by remember { mutableStateOf(true) }
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

//    if (showSplashScreen) {
//        SplashScreenView()
//    } else {
//        App(
//            hasShownOnBoarding = navigateToOnBoarding
//        )
//    }
    App(
        hasShownOnBoarding = navigateToOnBoarding
    )
}

@Composable
fun SplashScreenView() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0XFFF8F9CC)),
        contentAlignment = Alignment.Center
    ) {

//        Icon(
//
//            painter = painterResource(resource = Res.drawable.splash_theme),
//            contentDescription = "AppIcon"
//        )
        Text("mobile banking")

    }
}
