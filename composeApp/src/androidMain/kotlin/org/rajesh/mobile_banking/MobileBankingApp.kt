package org.rajesh.mobile_banking

import android.app.Application
import dev.rajesh.mobile_banking.di.androidKoinInit

class MobileBankingApp: Application() {

    override fun onCreate() {
        super.onCreate()
        androidKoinInit(this@MobileBankingApp)
    }
}