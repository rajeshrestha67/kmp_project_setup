package dev.rajesh.mobile_banking.loadWallet.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletLoadDetails
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeWalletLoadSuccessDetails
import dev.rajesh.mobile_banking.loadWallet.presentation.model.WalletLoadRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class WalletLoadUseCaseTest {
    private lateinit var walletRepository: WalletRepository
    private lateinit var walletLoadUseCase: WalletLoadUseCase

    @BeforeTest
    fun setUp() {
        walletRepository = mock()
        walletLoadUseCase = WalletLoadUseCase(walletRepository)
    }

    @Test
    fun invoke_should_return_success_when_api_returns_success() = runTest {
        //Arrange
        val walletLoadDetails = fakeWalletLoadSuccessDetails()
        val expectedResult: ApiResult<WalletLoadDetails, DataError> =
            ApiResult.Success(walletLoadDetails)
        val walletLoadRequest = WalletLoadRequest(
            senderAccountNumber = "00155555",
            walletId = "9840173991",
            walletUsername = "9840173991",
            amount = "1111",
            remarks = "test remarks",
            validationIdentifier = "123456"
        )
        everySuspend {
            walletRepository.walletLoad(walletLoadRequest)
        } returns expectedResult

        //Act
        val result = walletRepository.walletLoad(walletLoadRequest)

        //Assert
        result shouldBe expectedResult
        verifySuspend {
            walletRepository.walletLoad(walletLoadRequest)
        }

    }

    @Test
    fun walletLoad_should_return_error_when_api_fails() = runTest {
        val expectedErrorResult: ApiResult<WalletLoadDetails, DataError> = ApiResult.Error(
            DataError.NetworkError.DataUnknown
        )
        val walletLoadRequest = WalletLoadRequest(
            senderAccountNumber = "00155555",
            walletId = "9840173991",
            walletUsername = "9840173991",
            amount = "1111",
            remarks = "test remarks",
            validationIdentifier = "123456"
        )
        everySuspend {
            walletRepository.walletLoad(walletLoadRequest)
        } returns expectedErrorResult

        //Act
        val result = walletRepository.walletLoad(walletLoadRequest)

        //Assert
        result shouldBe expectedErrorResult
        verifySuspend { walletRepository.walletLoad(walletLoadRequest) }
    }


}