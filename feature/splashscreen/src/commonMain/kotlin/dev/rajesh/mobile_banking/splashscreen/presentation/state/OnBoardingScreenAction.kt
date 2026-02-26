package dev.rajesh.mobile_banking.splashscreen.presentation.state

sealed interface OnBoardingScreenAction {
    data object HasShownOnBoarding : OnBoardingScreenAction
    data object OnNext: OnBoardingScreenAction
    data object OnSkip: OnBoardingScreenAction

    data class SetCurrentPage(val page: Int) : OnBoardingScreenAction
}