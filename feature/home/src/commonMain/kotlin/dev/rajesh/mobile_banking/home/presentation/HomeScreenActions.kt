package dev.rajesh.mobile_banking.home.presentation

import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail

sealed interface HomeScreenActions {
    data object OnNotificationClicked : HomeScreenActions
    data object OnSwipeRefresh : HomeScreenActions

    data class OnBankingServiceClicked(val service: BankingServiceDetail) : HomeScreenActions
    data class OnQuickServiceClicked(val service: QuickServiceDetail) : HomeScreenActions
}