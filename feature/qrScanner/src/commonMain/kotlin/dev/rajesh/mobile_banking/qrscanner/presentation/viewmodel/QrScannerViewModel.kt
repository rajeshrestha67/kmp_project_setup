package dev.rajesh.mobile_banking.qrscanner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.usecase.GetBankListUseCase
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.usecases.FetchCoopBranchUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.GetWalletListUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.ErrorData
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.onError
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.qrscanner.domain.model.AccountDetails
import dev.rajesh.mobile_banking.qrscanner.domain.model.QPayMerchantDetail
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoder
import dev.rajesh.mobile_banking.qrscanner.domain.usecases.GetQPayMerchantDetailUseCase
import dev.rajesh.mobile_banking.qrscanner.presentation.state.QrNavigationEffect
import dev.rajesh.mobile_banking.qrscanner.presentation.state.QrScannerScreenAction
import dev.rajesh.mobile_banking.qrscanner.presentation.state.QrScannerScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QrScannerViewModel(
    private val getQPayMerchantDetailUseCase: GetQPayMerchantDetailUseCase,
    private val qrDecoder: QrDecoder,
    private val getBankListUseCase: GetBankListUseCase,
    private val fetchCoopBranchUseCase: FetchCoopBranchUseCase,
    private val getWalletListUseCase: GetWalletListUseCase
) : ViewModel() {


    private val _effect = Channel<QrNavigationEffect>()
    val effect = _effect.receiveAsFlow()
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
                    AppLogger.d(TAG, "Qr Scan Data: ${action.scannedData}")

                    getQPayMerchantDetail(action.scannedData)
                }
            }

            is QrScannerScreenAction.OnQrImageSelectedFromGallery -> {
                decodeQrFromImage(action.imageUri)
            }
        }
    }


    fun dismissError() {
        _state.update {
            it.copy(
                errorData = null,
                isFetchingQrMerchantDetails = false
            )
        }
    }

    private fun getQPayMerchantDetail(payload: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isFetchingQrMerchantDetails = true
            )
        }
        getQPayMerchantDetailUseCase(payload).onSuccess { resultData ->
            AppLogger.d(TAG, "fetch Q_pay merchant detail: Success: $resultData")
            _state.update {
                it.copy(
                    isFetchingQrMerchantDetails = false
                )
            }
            when {
                resultData.bankTransfer -> {
                    resultData.accountDetails?.let { accountDetails ->
                        getBankList(accountDetails)
                    }
                }

                resultData.internalFundTransfer -> {
                    resultData.accountDetails?.let { accountDetails ->
                        fetchCoopBranchList(accountDetails)
                    }
                }

                resultData.loadWallet -> {
                    getWalletList(resultData)
                }

                else -> {

                }

            }

        }.onError { error ->
            AppLogger.d(TAG, "fetch Q_pay merchant detail: Error: ${error.toErrorMessage()}")
            _state.update {
                it.copy(
                    isFetchingQrMerchantDetails = true,
                    errorData = ErrorData(error.toErrorMessage())
                )
            }
        }
    }

    private fun getBankList(accountDetails: AccountDetails) = viewModelScope.launch {
        _state.update {
            it.copy(isFetchingBankList = true)
        }
        getBankListUseCase.invoke()
            .onSuccess { bankList ->
                _state.update {
                    it.copy(isFetchingBankList = false)
                }
                val bank = bankList.find {
                    it.bankId.equals(accountDetails.bankCode, ignoreCase = false)
                }
                _effect.send(
                    QrNavigationEffect.ToInterbankTransfer(
                        accountDetails = accountDetails,
                        bank = bank
                    )
                )


            }
            .onError { error ->
                AppLogger.d(TAG, "Error: fetching bank list: ${error.toErrorMessage()}")
                _state.update {
                    it.copy(
                        isFetchingBankList = false,
                        errorData = ErrorData(error.toErrorMessage())
                    )
                }
            }
    }

    private fun fetchCoopBranchList(accountDetails: AccountDetails) = viewModelScope.launch {
        _state.update {
            it.copy(isFetchingCoopBranchList = true)
        }
        fetchCoopBranchUseCase.invoke()
            .onSuccess { branches ->
                _state.update {
                    it.copy(isFetchingCoopBranchList = false)
                }
                val branch = branches.find {
                    it.branchCode.equals(accountDetails.branchCode, ignoreCase = false)
                }
                _effect.send(
                    QrNavigationEffect.ToSameBankTransfer(
                        accountDetails = accountDetails,
                        branch = branch
                    )
                )
            }
            .onError {
                _state.update {
                    it.copy(isFetchingCoopBranchList = false)
                }
            }
    }

    private fun getWalletList(resultData: QPayMerchantDetail) = viewModelScope.launch {
        _state.update {
            it.copy(isFetchingWalletList = true)
        }
        getWalletListUseCase.invoke()
            .onSuccess { walletData ->
                _state.update {
                    it.copy(isFetchingWalletList = false)
                }

                val wallet = walletData.find {
                    it.name.equals(resultData.walletType, ignoreCase = true)
                }

                wallet?.let {
                    _effect.send(
                        QrNavigationEffect.ToWallet(
                            walletDetails = it,
                            walletUserId = resultData.walletId.orEmpty(),
                            walletHolderName = resultData.name.orEmpty()
                        )
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isFetchingWalletList = false,
                        errorData = ErrorData(error.toErrorMessage())
                    )
                }
            }
    }

    private fun decodeQrFromImage(path: String) = viewModelScope.launch {
        qrDecoder.decodeQrFromImage(path)
            .onSuccess {
                AppLogger.d(TAG, "decodeQrFromImage: Success $it")
                getQPayMerchantDetail(payload = it)
            }
            .onFailure {
                AppLogger.d(TAG, "decodeQrFromImage: Failed $it")
                _state.update {
                    it.copy(
                        errorData = ErrorData("Unable to read Qr")
                    )
                }
            }
    }


    companion object {
        const val TAG = "QrScannerViewModel"
    }

}