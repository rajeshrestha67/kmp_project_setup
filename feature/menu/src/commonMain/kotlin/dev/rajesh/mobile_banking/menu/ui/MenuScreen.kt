package dev.rajesh.mobile_banking.menu.ui

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
import dev.rajesh.mobile_banking.menu.ui.model.MenuScreenState
import dev.rajesh.mobile_banking.menu.ui.model.MenuScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MenuScreen() {
    val viewModel: MenuScreenViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    MenuScreenContent(state)
}

@Composable
fun MenuScreenContent(state: MenuScreenState) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding)
        ) {
            Column {
                Text("Menu Screen")
                Text("FullName: ${state.fullName} ${state.lastName} ")
            }
        }

    }
}