package dev.rajesh.mobile_banking.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

fun initKoin(config: KoinAppDeclaration? = null) {

    startKoin {
        config?.invoke(this)
        modules(
            NetworkModule().module,
            CryptographyModule().module,
            FormModule().module,
            PlatformModule().module,
            DataStoreModule().module,
            AuthModule().module,
            DashboardModule().module,
            UserModule().module,
            HomeScreenModule().module,
            MenuScreenModule().module,
            BankTransferModule().module,
            SameBankTransferModule().module,
            CoopBranchModule().module
        )
    }
}