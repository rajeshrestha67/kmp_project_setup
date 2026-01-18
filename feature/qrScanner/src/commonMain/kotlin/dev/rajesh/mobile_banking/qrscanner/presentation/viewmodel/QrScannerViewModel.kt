package dev.rajesh.mobile_banking.qrscanner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.ErrorData
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoder
import dev.rajesh.mobile_banking.qrscanner.domain.usecases.GetQPayMerchantDetailUseCase
import dev.rajesh.mobile_banking.qrscanner.presentation.state.QrScannerScreenAction
import dev.rajesh.mobile_banking.qrscanner.presentation.state.QrScannerScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QrScannerViewModel(
    private val getQPayMerchantDetailUseCase: GetQPayMerchantDetailUseCase,
    private val qrDecoder: QrDecoder
) : ViewModel() {

    private val _state = MutableStateFlow(QrScannerScreenState())

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = QrScannerScreenState()
    )

    fun onAction(action: QrScannerScreenAction) {
        when (action) {
            is QrScannerScreenAction.OnQrScanError -> {
                _state.update {
                    it.copy(
                        errorData = ErrorData(action.errorMsg)
                    )
                }
            }

            is QrScannerScreenAction.OnQrScanned -> {
                action.scannedData?.let {
//                    val decrypted: String? = decryptQrData(it, Constants.clientId)
//                    decrypted?.let {
//
//                    } ?: {
//                        getQPayMerchantDetail(action.scannedData)
//                    }
                    getQPayMerchantDetail(action.scannedData)
                }
            }

            is QrScannerScreenAction.OnQrImageSelectedFromGallery -> {
                decodeQrFromImage(action.imageUri)
            }
        }
    }



    fun dismissError() {
        _state.update { it.copy(errorData = null) }
    }

    private fun getQPayMerchantDetail(payload: String) = viewModelScope.launch {
        getQPayMerchantDetailUseCase(payload).onSuccess { resultData ->
            AppLogger.d(TAG, "fetch Q_pay merchant detail: Success: $resultData")
        }.onError { error ->
            AppLogger.d(TAG, "fetch Q_pay merchant detail: Error: ${error.toErrorMessage()}")
        }
    }

    private fun decodeQrFromImage(path: String) = viewModelScope.launch {
        qrDecoder.decodeQrFromImage(path)
            .onSuccess {
                AppLogger.d(TAG, "decodeQrFromImage: Success $it")
            }
            .onFailure {
                AppLogger.d(TAG, "decodeQrFromImage: Failed $it")
            }
    }


    companion object {
        const val TAG = "QrScannerViewModel"
    }

}