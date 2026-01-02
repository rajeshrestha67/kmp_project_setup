package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.viewmodel.InterBankTransferViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class OtherBankTransferModule {

    @KoinViewModel
    fun provideOtherBankTransferViewModel() = InterBankTransferViewModel()
}