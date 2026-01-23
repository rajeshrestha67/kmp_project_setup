package org.rajesh.mobile_banking.login.domain.usecase

import dev.rajesh.mobile_banking.login.domain.usecase.LoginUseCase
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlinx.coroutines.test.runTest
import org.rajesh.mobile_banking.login.fake.FakeTokenRepository
import org.rajesh.mobile_banking.login.fake.FakeUserRepository
import org.rajesh.mobile_banking.login.testUtils.fakeLoginRequest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LoginUseCaseTest {
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var fakeTokenRepository: FakeTokenRepository
    private lateinit var useCase: LoginUseCase

    @BeforeTest
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        fakeTokenRepository = FakeTokenRepository()
        useCase = LoginUseCase(
            userRepository = fakeUserRepository,
            tokenRepository = fakeTokenRepository
        )
    }

    @Test
    fun login_success_and_saves_token() = runTest {
        val request = fakeLoginRequest("9802304437", "2287")
        val result = useCase(request)
        assertTrue { result is ApiResult.Success }
        assertEquals("fake_token", fakeTokenRepository.savedToken?.jwtToken)
    }

    @Test
    fun login_error() = runTest {
        fakeUserRepository.result = ApiResult.Error(DataError.NetworkError.Custom("fake error"))
        val result = useCase(fakeLoginRequest("9802304437", "2287"))

        assertTrue(result is ApiResult.Error)
        assertNull(fakeTokenRepository.savedToken)
    }
}