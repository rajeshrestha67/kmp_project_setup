package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.notification.PushNotificationViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class PushNotificationModule {

    @KoinViewModel
    fun pushNotificationViewModel(): PushNotificationViewModel {
        return PushNotificationViewModel()
    }
}