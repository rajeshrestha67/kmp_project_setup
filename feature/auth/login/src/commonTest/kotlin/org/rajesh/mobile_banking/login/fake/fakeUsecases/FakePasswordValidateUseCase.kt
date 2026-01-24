package org.rajesh.mobile_banking.login.fake.fakeUsecases

class FakePasswordValidateUseCase {
    var shouldReturnError: String? = null

    operator fun invoke(password: String): String? = shouldReturnError
}