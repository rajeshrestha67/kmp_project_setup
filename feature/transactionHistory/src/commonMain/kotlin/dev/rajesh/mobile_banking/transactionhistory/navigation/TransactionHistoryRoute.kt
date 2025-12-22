package dev.rajesh.mobile_banking.transactionhistory.navigation

import kotlinx.serialization.Serializable

@Serializable

sealed interface TransactionHistoryRoute {

    @Serializable
    data object Root : TransactionHistoryRoute
}