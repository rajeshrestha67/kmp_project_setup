package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.qrscanner.presentation.viewmodel.QrScannerViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class QrScannerModule {

    @KoinViewModel
    fun qrScannerViewModel()= QrScannerViewModel ()
}