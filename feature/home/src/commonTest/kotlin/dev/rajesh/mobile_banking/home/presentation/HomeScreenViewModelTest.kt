package dev.rajesh.mobile_banking.home.presentation

import dev.rajesh.mobile_banking.home.domain.usecase.FetchBankingServiceUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.FetchQuickServicesUseCase
import dev.rajesh.mobile_banking.home.domain.usecase.GetGreetingUseCase
import dev.rajesh.mobile_banking.home.fakes.FakeBankingServiceRepository
import dev.rajesh.mobile_banking.home.fakes.FakeClock
import dev.rajesh.mobile_banking.home.fakes.FakeQuickServiceRepository
import dev.rajesh.mobile_banking.home.fakes.FakeUserDetailRepository
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.user.domain.usecase.FetchUserDetailUseCase
import dev.rajesh.mobile_banking.utils.DateUtils
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
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
import kotlin.time.Instant

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
}
