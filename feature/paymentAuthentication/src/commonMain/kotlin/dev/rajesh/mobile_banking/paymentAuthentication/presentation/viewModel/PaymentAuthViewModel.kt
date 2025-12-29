package dev.rajesh.mobile_banking.paymentAuthentication.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.state.PaymentAuthAction
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.state.PaymentAuthEffect
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.state.PaymentAuthEffect.*
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.state.PaymentAuthState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

class PaymentAuthViewModel : ViewModel() {

    private val _state = MutableStateFlow(PaymentAuthState())
    val state = _state
        .onStart {
            getMPin()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PaymentAuthState()
        )

    private val _paymentAuthEffect = MutableSharedFlow<PaymentAuthEffect>()
    val paymentAuthEffect: SharedFlow<PaymentAuthEffect> = _paymentAuthEffect.asSharedFlow()

    private val _errorChannel = Channel<String>()
    val errorChannel = _errorChannel.receiveAsFlow()


    fun onAction(action: PaymentAuthAction) {
        when (action) {
            is PaymentAuthAction.OnDigitClick -> {
                if (state.value.mPin.length < _state.value.mPinFromDS.length) {
                    _state.update {
                        it.copy(
                            mPin = it.mPin + action.digit
                        )
                    }
                }
            }

            PaymentAuthAction.OnConfirm -> {
                AppLogger.i(TAG, "mPin: ${state.value.mPin}")
                if (state.value.mPin.length in 4..6) {
                    if (state.value.mPin == _state.value.mPinFromDS) {
                        viewModelScope.launch {
                            _paymentAuthEffect.emit(
                                mPinAuthenticated(mPin = state.value.mPin)
                            )
                        }
                    } else {
                        viewModelScope.launch {
                            _errorChannel.send("Invalid MPin")
                        }
                    }

                }
            }

            PaymentAuthAction.OnDelete -> {
                _state.update {
                    it.copy(
                        mPin = it.mPin.dropLast(1)
                    )
                }
            }

            PaymentAuthAction.OnProceedWithBiometric -> {

            }

            PaymentAuthAction.OnProceedWithMPin -> {

            }

            PaymentAuthAction.ToggleVisibility -> {
                _state.update {
                    it.copy(isMpinVisible = !state.value.isMpinVisible)
                }
            }
        }
    }


    fun getMPin() {
        viewModelScope.launch {
            val tokenRepository: TokenRepository = getKoin().get()
            tokenRepository.token.firstOrNull()?.mPin?.let { pin ->
                _state.update {
                    it.copy(
                        mPinFromDS = pin
                    )
                }
            }
        }
    }

    companion object {
        const val TAG = "PaymentAuthViewModel"
    }
}