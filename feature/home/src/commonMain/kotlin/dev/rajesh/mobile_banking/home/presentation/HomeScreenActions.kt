package dev.rajesh.mobile_banking.home.presentation

sealed interface HomeScreenActions {
    data object OnNotificationClicked : HomeScreenActions
    data object OnSwipeRefresh : HomeScreenActions
}