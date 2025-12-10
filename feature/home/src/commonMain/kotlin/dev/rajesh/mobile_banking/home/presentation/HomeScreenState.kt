package dev.rajesh.mobile_banking.home.presentation

import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.res.SharedRes
import org.jetbrains.compose.resources.StringResource

data class HomeScreenState(
    val isRefreshing: Boolean = false,
    val showSwipeView: Boolean = false,

    val greetingMsg: StringResource = SharedRes.Strings.goodMorning,
    val fullName: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val accountNumber: String = "",
    val accountName: String = "",
    val actualBalance: String = "",
    val availableBalance: String = "",

    val isBankingServiceLoading: Boolean = false,
    val isQuickServiceLoading: Boolean = false,

    val bankingServicesList : List<BankingServiceDetail> = listOf(),
    val quickServicesList : List<QuickServiceDetail> = listOf()
)