package dev.rajesh.mobile_banking.home.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute {
    @Serializable
    data object Root: HomeRoute
    @Serializable
    data object Home: HomeRoute
}