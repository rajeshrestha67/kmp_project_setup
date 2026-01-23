package dev.rajesh.mobile_banking.domain.form

import dev.rajesh.mobile_banking.res.SharedRes
import io.kotest.matchers.shouldBe
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class MobileNumberValidateUseCaseTest : KoinTest {

    private val useCase: MobileNumberValidateUseCase by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                module {
                    singleOf(::MobileNumberValidateUseCase)
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun invalid_mobile_number_should_fail() {
        listOf(
            "980230443", //9 digit only
            "98023044371", //11 digits
            " 98023044371",// space with incorrect mobile number, 11 digits
            "A9802304437",
            "9802A304437", //letter in middle
            "_980230443", //includes special character
            "9802_30443", //includes special character
            "1234567890", //doesnt starts with 98, 97
            "9123456789" //starts with 9 but doesn't followed by 8 or 7

        ).forEach { mobileNumber ->
            useCase(mobileNumber) shouldBe SharedRes.Strings.invalidMobileNumber
        }
    }

    @Test
    fun empty_mobile_number_should_failed_with_required() {
        val mobileNumber = ""
        useCase(mobileNumber) shouldBe SharedRes.Strings.required
    }

    @Test
    fun correct_mobile_number_contains_white_space_should_pass() {
        listOf(
            "9802304437",
            " 9802304437", //space in front
            "9802 304437",
            "9802304437 ",
            "9779802304437",
            "977-9802304437",
            "+9779802304437",
            "+977-9802304437",
            "(+977)9802304437",
            "(977)9802304437"
        ).forEach { mobileNumber ->
            useCase(mobileNumber) shouldBe null
        }
    }
}