package org.rajesh.mobile_banking.login.data.repository

import dev.rajesh.mobile_banking.login.data.repository.UserRepositoryImpl
import dev.rajesh.mobile_banking.login.domain.repository.UserRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlinx.coroutines.test.runTest
import org.rajesh.mobile_banking.login.fake.FakeLoginRemoteDataSource
import org.rajesh.mobile_banking.login.testUtils.fakeLoginRequest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserRepositoryImplTest {
    private lateinit var fakeRemoteDS: FakeLoginRemoteDataSource
    private lateinit var repository: UserRepository

    @BeforeTest
    fun setUp() {
        fakeRemoteDS = FakeLoginRemoteDataSource()
        repository = UserRepositoryImpl(fakeRemoteDS)
    }

    @Test
    fun login_success_maps_dto_to_domain() = runTest {
        val result = repository.login(fakeLoginRequest("9802304437", "2287"))

        assertTrue(result is ApiResult.Success)
        assertEquals("fake_token", (result.data.access_token))
    }

    @Test
    fun login_error_propagates_error() = runTest {
        fakeRemoteDS.result = ApiResult.Error(DataError.NetworkError.Custom("Network Error"))
        val result = repository.login(fakeLoginRequest("9802304437", "2287"))
        assertTrue(result is ApiResult.Error)
    }
}