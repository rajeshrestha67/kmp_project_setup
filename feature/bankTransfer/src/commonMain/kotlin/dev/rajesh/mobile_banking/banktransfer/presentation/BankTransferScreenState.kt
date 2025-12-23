package dev.rajesh.mobile_banking.banktransfer.presentation

import dev.rajesh.mobile_banking.banktransfer.ui.model.BankTransferOption

class BankTransferScreenState {

    val bankTransferOptionList: List<BankTransferOption> = listOf(
        BankTransferOption(
            id = 1,
            title = "Same Bank",
            description = "Transfer fund to other accounts within same bank"
        ),
        BankTransferOption(
            id = 2,
            title = "Other Bank",
            description = "Transfer fund to other accounts in other bank"
        ),
        BankTransferOption(
            id = 3,
            title = "Favourite Accounts",
            description = "Transfer fund to your favourite accounts"
        ),
    )
}