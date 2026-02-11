package dev.rajesh.mobile_banking.home.domain.usecase

import dev.rajesh.mobile_banking.utils.DateUtils
import org.jetbrains.compose.resources.StringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GetGreetingUseCase(
    private val clock: Clock
) {
    operator fun invoke(): StringResource {
        return DateUtils.getGreetingForCurrentTime(clock)
    }
}