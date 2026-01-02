package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.state


sealed interface SelectBankAction {
    data class OnSearchTextChanged(val searchText: String) : SelectBankAction
}