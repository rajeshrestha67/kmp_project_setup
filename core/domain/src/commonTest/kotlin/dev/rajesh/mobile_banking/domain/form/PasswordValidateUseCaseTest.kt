package dev.rajesh.mobile_banking.domain.form

import dev.rajesh.mobile_banking.res.SharedRes
import io.kotest.matchers.shouldBe
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class PasswordValidateUseCaseTest {
    private lateinit var useCase: PasswordValidateUseCase

    @BeforeTest
    fun setUp() {
        useCase = PasswordValidateUseCase()
    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun valid_password_4_digits() {
        val password = "1234"
        val result = useCase(password)
        result shouldBe null
    }

    @Test
    fun valid_password_5_digits() {
        val password = "12345"
        val result = useCase(password)
        result shouldBe null
    }

    @Test
    fun valid_password_6_digits() {
        val password = "123456"
        val result = useCase(password)
        result shouldBe null
    }

    @Test
    fun empty_password_should_failed_required_rule() {
        val password = ""
        val result = useCase(password)
        result shouldBe SharedRes.Strings.required
    }

    @Test
    fun invalid_password_should_fail() {
        listOf(
            "123",
            " 123",
            "234 44",
            "2333 ",
            "1234567",
            "A123",
            "123A"
        ).forEach { password ->
            useCase(password) shouldBe SharedRes.Strings.invalidPasswordLength
        }
    }
}