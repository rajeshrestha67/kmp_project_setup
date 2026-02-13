package dev.rajesh.mobile_banking.home.fakes

import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.Service


fun fakeBankingService() = listOf(
    BankingServiceDetail(
        name = "Bank Transfer",
        uniqueIdentifier = "bank_transfer",
        type = "Dashboard",
        status = "Active",
        imageUrl = "some_url",
        appOrder = 1,
        new = false
    ),
    BankingServiceDetail(
        name = "Load Wallet",
        uniqueIdentifier = "load_wallet",
        type = "Dashboard",
        status = "Active",
        imageUrl = "some_url",
        appOrder = 2,
        new = false
    )
)

fun fakeQuickServices() = listOf(
    QuickServiceDetail(
        id = 1,
        name = "Top Up",
        imageUrl = "Some Url",
        uniqueIdentifier = "top_up",
        isNew = false,
        appOrder = 1,
        services = emptyList()
    ),
    QuickServiceDetail(
        id = 2,
        name = "NEA",
        imageUrl = "Some Url",
        uniqueIdentifier = "nea",
        isNew = false,
        appOrder = 2,
        services = emptyList()
    ),
)
