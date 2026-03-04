package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.database.configs.AppDatabase
import dev.rajesh.mobile_banking.database.dao.CoopBranchDetailDao
import dev.rajesh.mobile_banking.database.dao.CoopDetailDao
import dev.rajesh.mobile_banking.database.dao.UserDetailsDao
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DatabaseModule {

    @Single
    fun provideStudentDao(db: AppDatabase): UserDetailsDao = db.userDetailsDao()

    @Single
    fun provideCoopDetailDao(db: AppDatabase): CoopDetailDao = db.coopDetailsDao()

    @Single
    fun provideCoopBranchDetailDao(db: AppDatabase): CoopBranchDetailDao = db.coopBranchDetailDao()


}