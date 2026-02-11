package dev.rajesh.mobile_banking.home.fakes

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
class FakeClock(private val fixedInstant: Instant) : Clock {
    override fun now(): Instant = fixedInstant
}