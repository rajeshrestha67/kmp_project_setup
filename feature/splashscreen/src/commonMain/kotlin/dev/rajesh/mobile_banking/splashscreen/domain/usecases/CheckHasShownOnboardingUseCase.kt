package dev.rajesh.mobile_banking.splashscreen.domain.usecases

import dev.rajesh.mobile_banking.splashscreen.domain.UserAppPreferenceRepository
import kotlinx.coroutines.flow.firstOrNull

class CheckHasShownOnboardingUseCase(
    private val userAppPreferenceRepository: UserAppPreferenceRepository
) {
    suspend operator fun invoke(): Boolean {
        return userAppPreferenceRepository.userAppPreference.firstOrNull()?.hasShownOnBoarding
            ?: true
    }
}