package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.paymentAuthentication.presentation.viewModel.PaymentAuthViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class PaymentAuthModule {

    @KoinViewModel
    fun providePaymentAuthViewModel() = PaymentAuthViewModel()
}