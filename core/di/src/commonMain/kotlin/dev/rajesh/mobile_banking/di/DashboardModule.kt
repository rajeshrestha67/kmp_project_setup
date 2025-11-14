package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.dashboard.model.DashboardViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class DashboardModule {

    @KoinViewModel
    fun getDashboardViewModel() = DashboardViewModel()
}