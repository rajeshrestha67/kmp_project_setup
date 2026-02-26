package org.rajesh.mobile_banking

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import dev.rajesh.mobile_banking.di.androidKoinInit

class MobileBankingApp: Application() {

    override fun onCreate() {
        super.onCreate()
        androidKoinInit(this@MobileBankingApp)

        NotifierManager.initialize(
            NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_notification,
                showPushNotification = true
            )
        )
    }
}