package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.viewmodel.SameBankTransferViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class SameBankTransferModule {

    @KoinViewModel
    fun sameBankTransferViewModel() = SameBankTransferViewModel()
}