package dev.rajesh.mobile_banking.loadWallet.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.repository.WalletRepository
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeWalletList
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetWalletListUseCaseTest {
    private lateinit var walletRepository: WalletRepository
    private lateinit var getWalletListUseCase: GetWalletListUseCase

    @BeforeTest
    fun setUp(){
        walletRepository = mock()
        getWalletListUseCase = GetWalletListUseCase(walletRepository)
    }

    @Test
    fun invoke_should_return_wallet_list_when_repository_succeed() = runTest {
        //Arrange
        val walletList = fakeWalletList()

        val expectedResult: ApiResult<List<WalletDetail>, DataError> = ApiResult.Success(walletList)

        everySuspend {
            walletRepository.getWalletList()
        } returns expectedResult

        //Act
        val result = getWalletListUseCase()

        //Assert
        result shouldBe expectedResult
    }

    @Test
    fun invoke_should_return_error_when_repository_fails()= runTest {
        //Arrange
        val expectedResult: ApiResult<List<WalletDetail>, DataError> = ApiResult.Error(DataError.NetworkError.DataUnknown)

        everySuspend {
            walletRepository.getWalletList()
        } returns expectedResult

        //Act
        val result = getWalletListUseCase()

        //Assert
        result shouldBe expectedResult
        /**
         * This ensures:
         * The UseCase actually delegates to repository
         * It’s called exactly once
         */
        verifySuspend { walletRepository.getWalletList() }
    }
}