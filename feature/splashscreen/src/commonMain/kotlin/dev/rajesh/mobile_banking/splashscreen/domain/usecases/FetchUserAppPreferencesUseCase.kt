package dev.rajesh.mobile_banking.splashscreen.domain.usecases

import dev.rajesh.mobile_banking.splashscreen.domain.UserAppPreferenceRepository
import dev.rajesh.mobile_banking.splashscreen.domain.model.UserAppPreference
import kotlinx.coroutines.flow.Flow

class FetchUserAppPreferencesUseCase(
    private val userAppPreferenceRepository: UserAppPreferenceRepository
) {
    operator fun invoke(): Flow<UserAppPreference> {
        return userAppPreferenceRepository.userAppPreference
    }
}