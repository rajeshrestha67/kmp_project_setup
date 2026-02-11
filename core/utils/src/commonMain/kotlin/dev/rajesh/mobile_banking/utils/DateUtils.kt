package dev.rajesh.mobile_banking.utils

import dev.rajesh.mobile_banking.res.SharedRes
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
object DateUtils {

    fun getCurrentTime(): String {
        val now = Clock.System.now()
        val localTime = now.toLocalDateTime(TimeZone.currentSystemDefault()).time
        val finalTime = localTime.hour.toString() + ":" + localTime.minute.toString()
        return finalTime
    }

    fun getGreetingForCurrentTime(clock: Clock = Clock.System): StringResource {
        val now = clock.now()
        val localTime = now.toLocalDateTime(TimeZone.currentSystemDefault()).time

        print("local time hour: ${localTime.hour}")
        return when (localTime.hour) {
            in 5..11 -> SharedRes.Strings.goodMorning
            in 12..16 -> SharedRes.Strings.goodAfternoon
            in 17..20 -> SharedRes.Strings.goodEvening
            else -> SharedRes.Strings.goodEvening
        }
    }
}