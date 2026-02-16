package dev.rajesh.mobile_banking.testUtils.fakes

import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
import dev.rajesh.mobile_banking.user.domain.model.UserDetails

val fakeAccountDetails: List<AccountDetail> = listOf(
    AccountDetail(
        accountType = "Savings",
        availableBalance = "500",
        actualBalance = "500",
        primary = "true",
        accountNumber = "222",
        accountHolderName = "John Doe",
    )
)

var fakeUser: UserDetails = UserDetails(
    firstName = "John",
    lastName = "Doe",
    fullName = "John Doe",
    accountDetail = fakeAccountDetails
)