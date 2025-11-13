package org.rajesh.mobile_banking.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {
    @Serializable
    data object  LoginRoute: AppRoute

    @Serializable
    data object  DashboardRoute: AppRoute

    @Serializable
    data object OnBoardingRoute: AppRoute
}