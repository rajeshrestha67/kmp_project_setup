package org.rajesh.mobile_banking.login.fake.fakeUsecases

import dev.rajesh.mobile_banking.res.SharedRes


class FakeMobileNumberValidateUseCase {
    var shouldReturnError: String? = null

    operator fun invoke(mobileNumber: String): String? = shouldReturnError
}