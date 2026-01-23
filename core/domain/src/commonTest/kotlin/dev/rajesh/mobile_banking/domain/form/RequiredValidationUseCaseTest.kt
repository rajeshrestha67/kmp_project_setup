package dev.rajesh.mobile_banking.domain.form

import dev.rajesh.mobile_banking.res.SharedRes
import io.kotest.matchers.shouldBe
import kotlin.test.BeforeTest
import kotlin.test.Test

class RequiredValidationUseCaseTest {

    private lateinit var useCase: RequiredValidationUseCase

    @BeforeTest
    fun setUp() {
        useCase = RequiredValidationUseCase()
    }

    @Test
    fun empty_value_should_failed_required_rule() {
        val value = ""
        val result = useCase(value)
        result shouldBe SharedRes.Strings.required
    }

    @Test
    fun not_empty_value_should_pass_required_rule() {
        val value = "test"
        val result = useCase(value)
        result shouldBe null
    }

}