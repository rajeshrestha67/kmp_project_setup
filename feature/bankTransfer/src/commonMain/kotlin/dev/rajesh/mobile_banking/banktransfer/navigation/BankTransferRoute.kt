package dev.rajesh.mobile_banking.banktransfer.navigation

import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.utils.encodeUrl
import dev.rajesh.mobile_banking.utils.serialization.AppJson

object BankTransferRoute {
    const val root = "bank_transfer" //root = entry
    const val graph = "bank_transfer_graph"
    const val sameBank = "same_bank"
    const val otherBank = "other_bank"
    const val favouriteAccounts = "favourite_accounts"

    const val coopBranch = "coopBranch"
    const val confirmation = "confirmation"

    fun confirmationRoute(data: String): String {
        //return "confirmation?data=${URLEncoder.encode(data, "UTF-8")}"
        //val encodedData = data.encodeUrl()
        return "$confirmation?data=${data}"
    }
}

