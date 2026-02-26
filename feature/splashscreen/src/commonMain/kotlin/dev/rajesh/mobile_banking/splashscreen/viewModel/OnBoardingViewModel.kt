package dev.rajesh.mobile_banking.splashscreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.splashscreen.domain.usecases.CheckHasShownOnboardingUseCase
import dev.rajesh.mobile_banking.splashscreen.domain.usecases.UpdateHasShownOnBoardingUseCase
import dev.rajesh.mobile_banking.splashscreen.presentation.state.OnBoardingScreenAction
import dev.rajesh.mobile_banking.splashscreen.presentation.state.OnBoardingScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val checkHasShownOnboardingUseCase: CheckHasShownOnboardingUseCase,
    private val updateHasShownOnBoardingUseCase: UpdateHasShownOnBoardingUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(OnBoardingScreenState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = OnBoardingScreenState()
        )

    private val _navigationChannel = Channel<Boolean>()
    val navigationChannel = _navigationChannel.receiveAsFlow()

    fun action(action: OnBoardingScreenAction) {
        when (action) {
            is OnBoardingScreenAction.HasShownOnBoarding -> checkHasShownOnBoarding()

            is OnBoardingScreenAction.OnNext -> {
                if (state.value.currentPage < _state.value.screens.size - 1) {
                    val nextPage =
                        (_state.value.currentPage + 1).coerceAtMost(_state.value.screens.lastIndex)
                    setCurrentPage(nextPage)
                } else {
                    viewModelScope.launch {
                        updateHasShownOnBoardingUseCase()
                        _navigationChannel.send(true)
                    }
                }
            }

            OnBoardingScreenAction.OnSkip -> {
                viewModelScope.launch {
                    updateHasShownOnBoardingUseCase()
                    _navigationChannel.send(true)
                }
            }


            is OnBoardingScreenAction.SetCurrentPage -> {
                setCurrentPage(action.page)
            }

        }
    }

    private fun setCurrentPage(page: Int) {
        _state.update {
            val isLastPage = page == it.screens.size - 1
            it.copy(
                currentPage = page,
                title = if (isLastPage) SharedRes.Strings.getStarted else SharedRes.Strings.next
            )
        }
    }

    private fun checkHasShownOnBoarding() = viewModelScope.launch {
        _navigationChannel.send(checkHasShownOnboardingUseCase())
    }
}