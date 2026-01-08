package dev.rajesh.mobile_banking.loadWallet.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.presentation.ui.LoadWalletScreen
import dev.rajesh.mobile_banking.loadWallet.presentation.ui.WalletListScreen
import dev.rajesh.mobile_banking.utils.serialization.AppJson

fun NavGraphBuilder.loadWalletNavGraph(navController: NavController) {

    // wallet list
    composable(LoadWalletRoute.root) {
        WalletListScreen(
            onWalletClicked = { walletDetail ->
                val json = AppJson.encodeToString(
                    WalletDetail.serializer(),
                    walletDetail
                )

                navController.navigate(LoadWalletRoutes.LoadWalletDetail(json))
            },
            onBackClicked = {
                navController.popBackStack()
            }
        )
    }

    // wallet details
    composable<LoadWalletRoutes.LoadWalletDetail> {
        val walletDetailJson: String? = it.toRoute<LoadWalletRoutes.LoadWalletDetail>().json
        walletDetailJson?.let { json ->
            val walletDetail = AppJson.decodeFromString<WalletDetail>(json)
            it.savedStateHandle.remove<String>(LoadWalletRoute.root)
            LoadWalletScreen(
                walletDetail = walletDetail,
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

    }
}