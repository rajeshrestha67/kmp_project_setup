package dev.rajesh.mobile_banking.domain.form

import dev.rajesh.mobile_banking.res.SharedRes
import io.kotest.matchers.shouldBe
import kotlin.test.BeforeTest
import kotlin.test.Test

class UsernameValidateUseCaseTest {

    private lateinit var useCase: UsernameValidateUseCase

    @BeforeTest
    fun setUp() {
        useCase = UsernameValidateUseCase()
    }

    @Test
    fun empty_username_should_failed_required_rule() {
        val username = ""
        useCase(username) shouldBe SharedRes.Strings.required
    }

    @Test
    fun valid_username_should_pass_username_rule() {
        val username = "9802304437"
        useCase(username) shouldBe null
    }

    @Test
    fun invalid_username_should_fail() {
        listOf(
            "dfd",
           // "dfsdfsdfsdfsdfsdfsf",
        ).forEach { username ->
            useCase(username) shouldBe SharedRes.Strings.invalidUsername
        }
    }
}