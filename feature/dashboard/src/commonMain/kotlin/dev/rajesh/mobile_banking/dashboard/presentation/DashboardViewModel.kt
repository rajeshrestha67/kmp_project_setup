package dev.rajesh.mobile_banking.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.dashboard.presentation.route.DashboardRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DashboardViewModel() : ViewModel() {
    private val _state = MutableStateFlow(DashboardScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardScreenState()
    )

    fun action(action: DashboardScreenAction) {
        when (action) {
            is DashboardScreenAction.OnChangeScreen -> {
                navigateTo(action.route)
            }
        }
    }

    private fun navigateTo(route: DashboardRoute) {
        _state.update {
            it.copy(currentScreen = route)
        }
    }
}