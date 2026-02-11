package dev.rajesh.mobile_banking.home.domain.useCase

import dev.rajesh.mobile_banking.home.domain.usecase.GetGreetingUseCase
import dev.rajesh.mobile_banking.home.fakes.FakeClock
import dev.rajesh.mobile_banking.res.SharedRes
import io.kotest.matchers.shouldBe
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.todayIn
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class GetGreetingsUseCaseTest {
    @OptIn(ExperimentalTime::class)
    private fun createUseCaseAt(hour: Int, minute: Int = 0): GetGreetingUseCase {
        val timeZone = TimeZone.currentSystemDefault()
        val today = Clock.System.todayIn(timeZone)
        val instant = today.atTime(hour, minute).toInstant(timeZone)

        val fakeClock = FakeClock(instant)
        return GetGreetingUseCase(fakeClock)
    }

    @Test
    fun should_return_correct_greetings_based_on_hour() {
        listOf(
            Triple(5, 0, SharedRes.Strings.goodMorning),
            Triple(10, 30, SharedRes.Strings.goodMorning),
            Triple(11, 59, SharedRes.Strings.goodMorning),

            Triple(12, 0, SharedRes.Strings.goodAfternoon),
            Triple(14, 30, SharedRes.Strings.goodAfternoon),
            Triple(16, 0, SharedRes.Strings.goodAfternoon),

            Triple(17, 0, SharedRes.Strings.goodEvening),
            Triple(19, 30, SharedRes.Strings.goodEvening),
            Triple(22, 0, SharedRes.Strings.goodEvening),
        ).forEach { (hour, minute, expected) ->
            val useCase = createUseCaseAt(hour, minute)
            val result = useCase()
            result shouldBe expected
        }
    }
}