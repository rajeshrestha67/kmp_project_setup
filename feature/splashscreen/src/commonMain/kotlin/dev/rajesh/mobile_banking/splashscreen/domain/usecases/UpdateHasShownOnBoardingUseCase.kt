package dev.rajesh.mobile_banking.splashscreen.domain.usecases

import dev.rajesh.mobile_banking.splashscreen.domain.UserAppPreferenceRepository
import dev.rajesh.mobile_banking.splashscreen.domain.model.UserAppPreference
import kotlinx.coroutines.flow.firstOrNull

class UpdateHasShownOnBoardingUseCase(
    private val userAppPreferenceRepository: UserAppPreferenceRepository
) {
    suspend operator fun invoke() {
        val userAppPreference =
            userAppPreferenceRepository.userAppPreference.firstOrNull() ?: UserAppPreference()
        userAppPreferenceRepository.saveUserAppPreference(userAppPreference.copy(hasShownOnBoarding = true))
    }
}