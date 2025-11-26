package dev.rajesh.mobile_banking.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rajesh.mobile_banking.home.presentation.HomeScreenState
import dev.rajesh.mobile_banking.home.presentation.HomeScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {

    val viewModel: HomeScreenViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreenContent(state = state)
}

@Composable
fun HomeScreenContent(state: HomeScreenState) {
    Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(contentPadding)
            ) {
                Text("Home Screen")
                Text(state.fullName)
            }
        }

    }
}