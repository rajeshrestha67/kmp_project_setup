package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.differentBankTransfer.presentation.viewmodel.OtherBankTransferViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class OtherBankTransferModule {

    @KoinViewModel
    fun provideOtherBankTransferViewModel() = OtherBankTransferViewModel()
}