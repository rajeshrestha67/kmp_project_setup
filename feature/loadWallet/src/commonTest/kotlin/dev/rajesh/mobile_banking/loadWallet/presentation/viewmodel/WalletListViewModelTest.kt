package dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel

import dev.rajesh.mobile_banking.loadWallet.domain.usecase.GetWalletListUseCase
import dev.rajesh.mobile_banking.loadWallet.fakes.FakeWalletRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WalletListViewModelTest {
    private lateinit var walletRepository: FakeWalletRepository
    private lateinit var getWalletListUseCase: GetWalletListUseCase
    private lateinit var walletViewModel: WalletListViewModel


    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        walletRepository = FakeWalletRepository()
        getWalletListUseCase = GetWalletListUseCase(walletRepository)
        walletViewModel = WalletListViewModel(getWalletListUseCase = getWalletListUseCase)
    }

    @AfterTest
    fun tearDown(){
        Dispatchers.resetMain()
    }


    @Test
    fun init_should_return_list_of_wallets() = runTest {
        walletRepository.errorOnWalletFetchWalletList = false

        walletViewModel = WalletListViewModel(getWalletListUseCase = getWalletListUseCase)

        //Act
        advanceUntilIdle()
        val state = walletViewModel.state.value

        //Assert
        state.walletList shouldBe walletRepository.walletList
        state.isLoading shouldBe false
        state.errorMessage shouldBe null
    }

    @Test
    fun fetchWalletList_should_show_error_when_api_fails() = runTest {
        //Arrange
        walletRepository.errorOnWalletFetchWalletList = true

        //Act
        walletViewModel = WalletListViewModel(getWalletListUseCase = getWalletListUseCase)
        advanceUntilIdle()
        val state = walletViewModel.state.value

        //Assert
        state.walletList shouldBe emptyList()
        state.isLoading shouldBe false
        state.errorMessage shouldBe walletRepository.errorMessage
    }
}