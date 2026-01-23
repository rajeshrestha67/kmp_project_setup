package org.rajesh.mobile_banking.login.testUtils

import dev.rajesh.mobile_banking.login.domain.model.LoginRequest

fun fakeLoginRequest(username: String, password: String) = LoginRequest(
    username = username,
    password = password,
    clientId = "id",
    clientSecret = "secret",
    grantType = "password",
    deviceUniqueIdentifier = "device"
)