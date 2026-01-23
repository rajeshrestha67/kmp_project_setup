package dev.rajesh.mobile_banking.domain.form

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class MobileNumberValidateUseCaseTest: KoinTest {

    private val useCase: MobileNumberValidateUseCase by inject()

    @BeforeTest
    fun setUp(){
        startKoin {
            modules(
                module {
                    singleOf(::MobileNumberValidateUseCase)
                }
            )
        }
    }

    @AfterTest
    fun tearDown(){
        stopKoin()
    }


}