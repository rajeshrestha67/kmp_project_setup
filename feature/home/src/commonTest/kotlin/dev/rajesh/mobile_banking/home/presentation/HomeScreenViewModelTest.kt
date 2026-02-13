package dev.rajesh.mobile_banking.home.presentation

import dev.rajesh.mobile_banking.home.domain.usecase.FetchBankingServiceUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.FetchQuickServicesUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.GetGreetingUseCase
import dev.rajesh.mobile_banking.home.fakes.FakeBankingServiceRepository
import dev.rajesh.mobile_banking.home.fakes.FakeClock
import dev.rajesh.mobile_banking.home.fakes.FakeQuickServiceRepository
import dev.rajesh.mobile_banking.home.fakes.FakeUserDetailRepository
import dev.rajesh.mobile_banking.home.fakes.fakeBankingService
import dev.rajesh.mobile_banking.home.fakes.fakeQuickServices
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.todayIn
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class HomeScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeUserDetailRepository: FakeUserDetailRepository
    private lateinit var fakeBankingServiceRepository: FakeBankingServiceRepository
    private lateinit var fakeQuickServiceRepository: FakeQuickServiceRepository

    private lateinit var fetchUserDetailUseCase: FetchUserDetailUseCase
    private lateinit var fetchBankingServiceUseCase: FetchBankingServiceUseCase
    private lateinit var fetchQuickServicesUseCase: FetchQuickServicesUseCase


    private lateinit var homeScreenViewModel: HomeScreenViewModel

    @OptIn(ExperimentalTime::class)
    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeUserDetailRepository = FakeUserDetailRepository()
        fakeBankingServiceRepository = FakeBankingServiceRepository()
        fakeQuickServiceRepository = FakeQuickServiceRepository()

        fetchUserDetailUseCase = FetchUserDetailUseCase(fakeUserDetailRepository)
        fetchBankingServiceUseCase = FetchBankingServiceUseCase(fakeBankingServiceRepository)
        fetchQuickServicesUseCase = FetchQuickServicesUseCase(fakeQuickServiceRepository)

        homeScreenViewModel = HomeScreenViewModel(
            userDetailUseCase = fetchUserDetailUseCase,
            fetchBankingServiceUseCase = fetchBankingServiceUseCase,
            fetchQuickServicesUseCase = fetchQuickServicesUseCase,
            greetingUseCase = GetGreetingUseCase(Clock.System)
        )
        testDispatcher.scheduler.advanceUntilIdle()

    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalTime::class)
    private fun createViewModelAt(hour: Int, minute: Int = 0) {
        val timeZone = TimeZone.currentSystemDefault()
        val today = Clock.System.todayIn(timeZone)
        val instant = today.atTime(hour, minute).toInstant(timeZone)

        val fakeClock = FakeClock(instant)
        val greetingUseCase = GetGreetingUseCase(fakeClock)

        homeScreenViewModel = HomeScreenViewModel(
            userDetailUseCase = fetchUserDetailUseCase,
            fetchBankingServiceUseCase = fetchBankingServiceUseCase,
            fetchQuickServicesUseCase = fetchQuickServicesUseCase,
            greetingUseCase = greetingUseCase
        )
    }

    private fun createHomeViewModel() {
        homeScreenViewModel = HomeScreenViewModel(
            userDetailUseCase = fetchUserDetailUseCase,
            fetchBankingServiceUseCase = fetchBankingServiceUseCase,
            fetchQuickServicesUseCase = fetchQuickServicesUseCase,
            greetingUseCase = GetGreetingUseCase(Clock.System)
        )
    }

    @OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
    @Test
    fun getGreetingsForHour_morning_should_return_good_morning() = runTest {
        createViewModelAt(hour = 8, minute = 15)
        advanceUntilIdle()
        homeScreenViewModel.state.value.greetingMsg shouldBe SharedRes.Strings.goodMorning
    }

    @OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
    @Test
    fun getGreetingsForHour_afternoon_should_return_good_afternoon() = runTest {
        createViewModelAt(hour = 14, minute = 15)
        advanceUntilIdle()
        homeScreenViewModel.state.value.greetingMsg shouldBe SharedRes.Strings.goodAfternoon
    }

    @OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
    @Test
    fun getGreetingsForHour_evening_should_return_good_evening() = runTest {
        createViewModelAt(hour = 18, minute = 15)
        advanceUntilIdle()
        homeScreenViewModel.state.value.greetingMsg shouldBe SharedRes.Strings.goodEvening
    }

    /**
     * should load user details
     */

//    @Test
//    fun init_should_load_user_details_from_dataStore() = runTest {
//        fakeBankingServiceRepository.shouldReturnError = false
//        homeScreenViewModel = HomeScreenViewModel(
//            userDetailUseCase = fetchUserDetailUseCase,
//            fetchBankingServiceUseCase = fetchBankingServiceUseCase,
//            fetchQuickServicesUseCase = fetchQuickServicesUseCase,
//            greetingUseCase = GetGreetingUseCase(Clock.System)
//        )
//
//        advanceUntilIdle()
//        val state = homeScreenViewModel.state.value
//        state.fullName shouldBe "Will Smith"
//        state.firstName shouldBe "Will"
//        state.lastName shouldBe "Smith"
//        state.accountNumber shouldBe "222"
//        state.accountName shouldBe "Savings"
//        state.actualBalance shouldBe "500"
//        state.availableBalance shouldBe "500"
//        state.isRefreshing shouldBe false
//    }

    @Test
    fun init_should_load_user_details_from_api() = runTest {
        fakeBankingServiceRepository.shouldReturnError = false
        createHomeViewModel()
        fetchUserDetailUseCase.invoke(true)
        advanceUntilIdle()
        val state = homeScreenViewModel.state.value
        state.fullName shouldBe "John Doe"
        state.firstName shouldBe "John"
        state.lastName shouldBe "Doe"
        state.accountNumber shouldBe "222"
        state.accountName shouldBe "Savings"
        state.actualBalance shouldBe "500"
        state.availableBalance shouldBe "500"
        state.isRefreshing shouldBe false
    }


    //Banking services loading flow

    @OptIn(ExperimentalTime::class)
    @Test
    fun empty_bank_service_List() = runTest {
        fakeBankingServiceRepository.services = emptyList()

        createHomeViewModel()

        advanceUntilIdle()

        val state = homeScreenViewModel.state.value

        state.isBankingServiceLoading shouldBe false
        state.bankingServicesList shouldBe emptyList()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun should_load_banking_services_and_update_state() = runTest {

        advanceUntilIdle()
        val state = homeScreenViewModel.state.value

        state.isBankingServiceLoading shouldBe false
        state.bankingServicesList shouldBe fakeBankingServiceRepository.services
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun load_banking_services_fails() = runTest {
        fakeBankingServiceRepository.shouldReturnError = true

        createHomeViewModel()

        advanceUntilIdle()
        val state = homeScreenViewModel.state.value

        state.isBankingServiceLoading shouldBe false
        state.bankingServicesList shouldBe emptyList()
    }


    /**
     * Quick service test flow
     */

    @OptIn(ExperimentalTime::class)
    @Test
    fun empty_quick_service_List() = runTest {
        fakeQuickServiceRepository.quickServices = emptyList()

        createHomeViewModel()

        advanceUntilIdle()

        val state = homeScreenViewModel.state.value
        state.isQuickServiceLoading shouldBe false
        state.quickServicesList shouldBe emptyList()

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun should_load_quick_services_and_update_state() = runTest {
        advanceUntilIdle()
        val state = homeScreenViewModel.state.value

        state.isQuickServiceLoading shouldBe false
        state.quickServicesList shouldBe fakeQuickServiceRepository.quickServices
    }

    @Test
    fun quick_service_load_fails() = runTest {
        fakeQuickServiceRepository.shouldReturnError = true

        createHomeViewModel()

        advanceUntilIdle()

        val state = homeScreenViewModel.state.value

        state.isQuickServiceLoading shouldBe false
        state.quickServicesList shouldBe emptyList()

    }

    @Test
    fun onAction_banking_service_clicked_should_emit_action() = runTest {
        val service = fakeBankingService().firstOrNull()
        if (service != null) {
            homeScreenViewModel.onAction(HomeScreenActions.OnBankingServiceClicked(service))

            val emitted = homeScreenViewModel.actions.first()
            emitted shouldBe HomeScreenActions.OnBankingServiceClicked(service)
        }
    }

    @Test
    fun onAction_quick_service_clicked_should_emit_action() = runTest {
        val service = fakeQuickServices().firstOrNull()
        if (service != null) {
            homeScreenViewModel.onAction(HomeScreenActions.OnQuickServiceClicked(service))
            val emitted = homeScreenViewModel.actions.first()
            emitted shouldBe HomeScreenActions.OnQuickServiceClicked(service)
        }
    }

    @Test
    fun onAction_qr_click_emits_action() = runTest {
        homeScreenViewModel.onAction(
            HomeScreenActions.OnQrScannerClicked
        )

        val emitted = homeScreenViewModel.actions.first()

        emitted shouldBe HomeScreenActions.OnQrScannerClicked
    }


}
