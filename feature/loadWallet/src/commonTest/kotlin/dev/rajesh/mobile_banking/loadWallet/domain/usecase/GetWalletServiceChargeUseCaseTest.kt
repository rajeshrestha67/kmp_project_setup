package dev.rajesh.mobile_banking.loadWallet.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetWalletServiceChargeUseCaseTest {
    private lateinit var walletRepository: WalletRepository
    private lateinit var getWalletServiceChargeUseCase: GetWalletServiceChargeUseCase


    @BeforeTest
    fun setUp() {
        walletRepository = mock()
        getWalletServiceChargeUseCase = GetWalletServiceChargeUseCase(walletRepository)
    }

    @Test
    fun invoke_should_return_service_charge_when_repository_succeed() = runTest {
        //Arrange
        val serviceCharge = WalletChargeDetail(
            code = "M000",
            details = 10.0,
            message = "Charge fetched successfully",
            status = "success"
        )

        val expectedResult: ApiResult<WalletChargeDetail, DataError> =
            ApiResult.Success(serviceCharge)

        everySuspend {
            walletRepository.getWalletCharge(
                "1000",
                "walletId",
                "SERVICE"
            )
        } returns expectedResult

        //Act
        val result = getWalletServiceChargeUseCase(
            "1000",
            "walletId",
            "SERVICE"
        )

        //Assert
        result shouldBe expectedResult
        verifySuspend {
            walletRepository.getWalletCharge("1000", "walletId", "SERVICE")
        }
    }

    @Test
    fun invoke_should_return_error_when_repository_fails() = runTest {
       //Arrange
        val expectedErrorResult: ApiResult<WalletChargeDetail, DataError> =
            ApiResult.Error(DataError.NetworkError.DataUnknown)

        everySuspend {
            walletRepository.getWalletCharge(
                "1000",
                "walletId",
                "SERVICE"
            )
        } returns expectedErrorResult

        //Act
        val result = getWalletServiceChargeUseCase(
            "1000",
            "walletId",
            "SERVICE"
        )

        //Assert
        result shouldBe expectedErrorResult
        verifySuspend {
            walletRepository.getWalletCharge(
                "1000",
                "walletId",
                "SERVICE"
            )
        }
    }

}
