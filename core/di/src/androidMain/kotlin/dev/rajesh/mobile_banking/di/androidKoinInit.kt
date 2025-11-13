package dev.rajesh.mobile_banking.di

import android.content.Context
import org.koin.android.ext.koin.androidContext


fun androidKoinInit(context: Context){
    initKoin {
        androidContext(context)
    }
}