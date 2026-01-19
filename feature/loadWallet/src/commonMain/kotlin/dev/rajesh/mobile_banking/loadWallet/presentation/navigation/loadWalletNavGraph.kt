package dev.rajesh.mobile_banking.loadWallet.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.rajesh.mobile_banking.confirmation.ConfirmationConstant
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.confirmation.presentation.ConfirmationScreen
import dev.rajesh.mobile_banking.loadWallet.domain.model.QrWallet
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.presentation.ui.LoadWalletScreen
import dev.rajesh.mobile_banking.loadWallet.presentation.ui.WalletListScreen
import dev.rajesh.mobile_banking.paymentAuthentication.PaymentAuthResult
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.PaymentAuthenticationScreen
import dev.rajesh.mobile_banking.transactionsuccess.TransactionSuccessFulScreen
import dev.rajesh.mobile_banking.transactionsuccess.constants.TransactionSuccessfulConstant
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionData
import dev.rajesh.mobile_banking.utils.serialization.AppJson

fun NavGraphBuilder.loadWalletNavGraph(
    navController: NavController,
    onExitToDashboard: () -> Unit
) {

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
        val walletUserId: String? = it.toRoute<LoadWalletRoutes.LoadWalletDetail>().walletUserId
        val walletHolderName: String? =
            it.toRoute<LoadWalletRoutes.LoadWalletDetail>().walletHolderName

        var walletDetail: WalletDetail? = null
        walletDetailJson?.let { json ->
            walletDetail = AppJson.decodeFromString<WalletDetail>(json)

            it.savedStateHandle.remove<String>(LoadWalletRoute.root)
            LoadWalletScreen(
                navController = navController,
                walletDetail = walletDetail,
                walletUserId = walletUserId,
                walletHolderName = walletHolderName,
                showConfirmation = {
                    val json = AppJson.encodeToString(
                        ConfirmationData.serializer(),
                        it
                    )
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set(
                            ConfirmationConstant.CONFIRMATION_DATA, json
                        )

                    navController.navigate(LoadWalletRoutes.ConfirmationRoute(json))
                },
                showTransactionSuccessful = { transactionData ->
                    val transactionDataJson = AppJson.encodeToString(
                        TransactionData.serializer(),
                        transactionData
                    )
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set(TransactionSuccessfulConstant.TRANSACTION_DATA, transactionDataJson)

                    navController.navigate(
                        LoadWalletRoutes.TransactionSuccessfulRoute(
                            transactionDataJson
                        )
                    )
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

    }

    composable<LoadWalletRoutes.ConfirmationRoute> {
        val confirmationDataJson: String? = it.toRoute<LoadWalletRoutes.ConfirmationRoute>().json

        confirmationDataJson?.let { jsonData ->
            val confirmationData = AppJson.decodeFromString<ConfirmationData>(jsonData)
            it.savedStateHandle.remove<String>(ConfirmationConstant.CONFIRMATION_DATA)
            ConfirmationScreen(
                data = confirmationData,
                onConfirm = {
                    navController.navigate(LoadWalletRoutes.PaymentAuthenticationRoute) {
                        //remove confirmation screen from stack
                        popUpTo<LoadWalletRoutes.ConfirmationRoute> {
                            inclusive = true
                        }
                    }
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

    }

    composable<LoadWalletRoutes.PaymentAuthenticationRoute> {
        PaymentAuthenticationScreen(
            onMPinVerified = { mPin ->
                navController.getBackStackEntry<LoadWalletRoutes.LoadWalletDetail>()
                    .savedStateHandle
                    .set(PaymentAuthResult.mPin, mPin)

                //clear auth and confirmation screen
                /* navController.popBackStack(
                     route = LoadWalletRoutes.LoadWalletDetail,
                     inclusive = false
                 )*/
                navController.popBackStack()
            },

            onBackClicked = {
                navController.popBackStack()
            })
    }

    composable<LoadWalletRoutes.TransactionSuccessfulRoute> {
        val transactionDataJson: String? =
            it.toRoute<LoadWalletRoutes.TransactionSuccessfulRoute>().json

        transactionDataJson?.let { jsnData ->
            val transactionData = AppJson.decodeFromString<TransactionData>(jsnData)
            it.savedStateHandle.remove<String>(TransactionSuccessfulConstant.TRANSACTION_DATA)
            TransactionSuccessFulScreen(
                data = transactionData,
                goToDashboardClicked = {
                    onExitToDashboard()
                }
            )

        }
    }


}