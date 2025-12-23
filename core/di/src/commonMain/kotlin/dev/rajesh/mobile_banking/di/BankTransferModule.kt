package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.presentation.BankTransferScreenViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class BankTransferModule {

    @KoinViewModel
    fun bankTransferViewModel() = BankTransferScreenViewModel()
}