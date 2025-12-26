package dev.rajesh.mobile_banking.loadwallet.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.rajesh.mobile_banking.loadwallet.presentation.LoadWalletScreen

fun NavGraphBuilder.loadWalletNavGraph(navController: NavController){
    composable(LoadWalletRoute.root){
        LoadWalletScreen()
    }

    //load wallet details


}