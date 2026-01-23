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

class EmailValidateUseCaseTest : KoinTest {

    private val useCase: EmailValidateUseCase by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                modules = module {
                    singleOf(::EmailValidateUseCase)
                }
            )

        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun empty_email_should_fail_required_rule() {
        val email = ""
        val result = useCase(email)
        result shouldBe SharedRes.Strings.required
    }

    @Test
    fun invalid_emails_should_fail(){
        listOf(
            "test@",
            "@example.com",
            "testexample.com",
            "test@examplecom",
            "test@.com",
            "test@com",
            "test12***@example.com",
            "test1!@example.com",
            " test@example.com",
            "test@example.com ",
            "te st@example.com",
        ).forEach { email->
            useCase(email) shouldBe SharedRes.Strings.invalidEmailAddress
        }
    }

    @Test
    fun valid_simple_email_should_pass_email_rule() {
        val email = "test@example.com"
        val result = useCase(email)
        result shouldBe null
    }

    @Test
    fun valid_email_with_number_should_pass_email_rule() {
        val email = "test11@example.com"
        val result = useCase(email)
        result shouldBe null
    }


    @Test
    fun valid_email_with_special_characters_should_pass_email_rule() {
        val email = "test_hello-123@example.com"
        val result = useCase(email)
        result shouldBe null
    }

 @Test
    fun valid_email_with_sub_domain_should_pass() {
        val email = "test@example.com.np"
        val result = useCase(email)
        result shouldBe null
    }




}