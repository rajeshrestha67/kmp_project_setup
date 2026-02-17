package dev.rajesh.mobile_banking.loadWallet.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationDetailDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletValidationResponseDTO
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeValidationResponse
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ValidateWalletUseCaseTest {

    private lateinit var walletRepository: WalletRepository
    private lateinit var validateWalletUseCase: ValidateWalletUseCase

    private val walletId = "1"
    private val walletUsername = "9802304437"
    private val amount = "1000"

    @BeforeTest
    fun setUp() {
        walletRepository = mock()
        validateWalletUseCase = ValidateWalletUseCase(walletRepository)
    }

    @Test
    fun invoke_should_return_validation_detail_when_repository_succeed() = runTest {
        //Arrange
        val validationDetails = WalletValidationDetail(
            message = "Wallet validated successfully",
            status = "success",
            customerName = "John Doe",
            customerProfileImageUrl = "https://example.com/profile.jpg",
            validationIdentifier = "123456"
        )

        val expectedResult: ApiResult<WalletValidationDetail, DataError> =
            ApiResult.Success(validationDetails)

        everySuspend {
            walletRepository.validateWallet(
                walletId = walletId,
                walletUsername = walletUsername,
                amount = amount
            )
        } returns expectedResult


        //Act
        val result = validateWalletUseCase(
            walletId = walletId,
            walletUsername = walletUsername,
            amount = amount
        )

        //Assert
        result shouldBe expectedResult
        verifySuspend {
            walletRepository.validateWallet(
                walletId = walletId,
                walletUsername = walletUsername,
                amount = amount
            )
        }

    }

    @Test
    fun invoke_should_return_error_when_validation_fails() = runTest {
        //Arrange
        val expectedResult: ApiResult<WalletValidationDetail, DataError> =
            ApiResult.Error(DataError.NetworkError.DataUnknown)

        everySuspend {
            walletRepository.validateWallet(
                walletId = walletId,
                walletUsername = walletUsername,
                amount = amount)
        } returns expectedResult

        //Act
        val result = validateWalletUseCase(
            walletId = walletId,
            walletUsername = walletUsername,
            amount = amount
        )

        //Assert
        result shouldBe expectedResult

        verifySuspend {
            walletRepository.validateWallet(
                walletId = walletId,
                walletUsername = walletUsername,
                amount = amount
            )
        }

    }


}