package dev.rajesh.mobile_banking.user.data.repository

import dev.rajesh.datastore.userData.repository.UserDetailLocalDataSource
import dev.rajesh.mobile_banking.database.dao.UserDetailsDao
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map
import dev.rajesh.mobile_banking.networkhelper.onSuccess
import dev.rajesh.mobile_banking.user.data.mapper.toDomain
import dev.rajesh.mobile_banking.user.data.mapper.toEntity
import dev.rajesh.mobile_banking.user.data.mapper.toUserDetails
import dev.rajesh.mobile_banking.user.data.mapper.toUserDetailsLocal
import dev.rajesh.mobile_banking.user.data.remote.UserDetailRemoteDataSource
import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.user.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.firstOrNull

class UserDetailRepositoryImpl(
    private val userDetailRemoteDataSource: UserDetailRemoteDataSource,
    private val userDetailLocalDataSource: UserDetailLocalDataSource,
    private val userDetailDao: UserDetailsDao
) : UserDetailRepository {
    override suspend fun fetchUserDetail(forceFetch: Boolean): ApiResult<UserDetails, DataError> {
//        if (!forceFetch) {
//            val localUser = userDetailDao.getAll().firstOrNull()?.firstOrNull()
//            if (localUser != null) {
//                return ApiResult.Success(localUser.toDomain())
//            }
//        }
        val localUser = userDetailDao.getAll().firstOrNull()?.firstOrNull()
        AppLogger.e("localUser", "${localUser}")

        return userDetailRemoteDataSource
            .fetchUserDetail()
            .map { dto ->
                val user = dto.details.toUserDetails()
                user
            }.onSuccess { data ->
                userDetailLocalDataSource.saveUserDetailsToDS(data.toUserDetailsLocal())
                userDetailDao.deleteAll()
                userDetailDao.insert(data.toEntity())
            }
    }

    override suspend fun fetchUserDetailFromDS(): UserDetails? {
        val userDetails = userDetailLocalDataSource.userDetailsLocalFlow.firstOrNull()
        return userDetails?.toDomain()
    }


}