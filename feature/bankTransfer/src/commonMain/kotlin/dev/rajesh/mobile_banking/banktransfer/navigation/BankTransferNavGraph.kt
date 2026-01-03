package dev.rajesh.mobile_banking.banktransfer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.BankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.presentation.ui.FavouriteAccountsScreen
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.presentation.ui.InterBankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui.SameBankTransferScreen
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.ui.SelectBranchScreen
import dev.rajesh.mobile_banking.confirmation.ConfirmationConstant
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.confirmation.presentation.ConfirmationScreen
import dev.rajesh.mobile_banking.paymentAuthentication.PaymentAuthResult
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.PaymentAuthenticationScreen
import dev.rajesh.mobile_banking.transactionsuccess.TransactionSuccessFulScreen
import dev.rajesh.mobile_banking.transactionsuccess.constants.TransactionSuccessfulConstant
import dev.rajesh.mobile_banking.transactionsuccess.model.TransactionData
import dev.rajesh.mobile_banking.utils.serialization.AppJson


fun NavGraphBuilder.bankTransferNavGraph(
    navController: NavController,
    onExitToDashboard: () -> Unit
) {
    composable(BankTransferRoute.root) {
        BankTransferScreen(
            onOptionSelected = { option ->
                when (option.id) {
                    1 -> navController.navigate(BankTransferRoutes.SameBankTransfer)
                    2 -> navController.navigate(BankTransferRoutes.OtherBankTransfer)
                    3 -> navController.navigate(
                        BankTransferRoutes.FavouriteAccounts
                    )
                }
            },
            onBackClicked = {
                navController.popBackStack()
            }
        )
    }

    composable<BankTransferRoutes.SameBankTransfer> {
        SameBankTransferScreen(
            navController = navController,
            onBackClicked = {
                navController.popBackStack()
            },
            onSelectCoopBranchClicked = {
                navController.navigate(BankTransferRoutes.SelectCoopBranch)
            },
            showConfirmation = { confirmationData ->
                val json = AppJson.encodeToString(
                    ConfirmationData.serializer(),
                    confirmationData
                )

                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        ConfirmationConstant.CONFIRMATION_DATA, json
                    )

                navController.navigate(BankTransferRoutes.Confirmation(json))
            },
            onTransactionSuccessful = { transactionData ->
                val transactionDataJson = AppJson.encodeToString(
                    TransactionData.serializer(),
                    transactionData
                )
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set(TransactionSuccessfulConstant.TRANSACTION_DATA, transactionDataJson)
                navController.navigate(BankTransferRoutes.TransactionSuccessful(transactionDataJson))
            }

        )
    }

    composable<BankTransferRoutes.OtherBankTransfer> {
        InterBankTransferScreen(
            navController = navController,
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
                navController.navigate(InterBankTransferRoutes.ConfirmationRoute(json))
            },

            onTransactionSuccessful = { transactionData ->
                val transactionDataJson = AppJson.encodeToString(
                    TransactionData.serializer(),
                    transactionData
                )
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set(TransactionSuccessfulConstant.TRANSACTION_DATA, transactionDataJson)

                navController.navigate(
                    InterBankTransferRoutes.TransactionSuccessfulRoute(
                        transactionDataJson
                    )
                )


            },

            onBackClicked = {
                navController.popBackStack()
            }
        )
    }

    composable<BankTransferRoutes.FavouriteAccounts> {
        FavouriteAccountsScreen(onBackClicked = {
            navController.popBackStack()
        })
    }

    composable<BankTransferRoutes.SelectCoopBranch> {
        SelectBranchScreen(
            onBackClicked = {
                navController.popBackStack()
            },
            onCoopBranchSelected = { coopBranch ->
                val branchJson = AppJson.encodeToString(CoopBranchDetail.serializer(), coopBranch)
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        BankTransferResult.SELECTED_COOP_BRANCH,
                        branchJson
                    )
                navController.popBackStack()
            }
        )
    }

    /**
     * SameBank Transfer Confirmation
     */
    composable<BankTransferRoutes.Confirmation> {
        val confirmationDataJson: String? = it.toRoute<BankTransferRoutes.Confirmation>().json

        confirmationDataJson?.let { jsonData ->
            val confirmationData = AppJson.decodeFromString<ConfirmationData>(jsonData)
            it.savedStateHandle.remove<String>(ConfirmationConstant.CONFIRMATION_DATA)
            ConfirmationScreen(
                data = confirmationData,
                onConfirm = {
                    // handle confirm
                    //navigate to mPIn authentication view
                    navController.navigate(BankTransferRoutes.PaymentAuthentication)
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

    }

    /**
     * Same Bank Transfer Payment Authentication
     */
    composable<BankTransferRoutes.PaymentAuthentication> {
        PaymentAuthenticationScreen(
            onMPinVerified = { mPin ->
                navController.getBackStackEntry<BankTransferRoutes.SameBankTransfer>()
                    .savedStateHandle
                    .set(PaymentAuthResult.mPin, mPin)

                //clear auth and confirmation screen
                navController.popBackStack(
                    route = BankTransferRoutes.SameBankTransfer,
                    inclusive = false
                )
            },

            onBackClicked = {
                navController.popBackStack()
            })
    }

    /**
     * Same bank Transfer successful
     */
    composable<BankTransferRoutes.TransactionSuccessful> {
        val transactionDataJson: String? =
            it.toRoute<BankTransferRoutes.TransactionSuccessful>().json

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

    /**
     * InterBank transfer confirmation
     */
    composable<InterBankTransferRoutes.ConfirmationRoute> {
        val confirmationDataJson: String? =
            it.toRoute<InterBankTransferRoutes.ConfirmationRoute>().json

        confirmationDataJson?.let { jsonData ->
            val confirmationData = AppJson.decodeFromString<ConfirmationData>(jsonData)
            it.savedStateHandle.remove<String>(ConfirmationConstant.CONFIRMATION_DATA)
            ConfirmationScreen(
                data = confirmationData,
                onConfirm = {
                    // handle confirm
                    //navigate to mPIn authentication view
                    navController.navigate(InterBankTransferRoutes.PaymentAuthenticationRoute)
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

    }

    /**
     * Inter bank transfer Payment Authentication
     */
    composable<InterBankTransferRoutes.PaymentAuthenticationRoute> {
        PaymentAuthenticationScreen(
            onMPinVerified = { mPin ->
                navController.getBackStackEntry<BankTransferRoutes.OtherBankTransfer>()
                    .savedStateHandle
                    .set(PaymentAuthResult.mPin, mPin)

                //clear auth and confirmation screen
                navController.popBackStack(
                    route = BankTransferRoutes.OtherBankTransfer,
                    inclusive = false
                )
            },

            onBackClicked = {
                navController.popBackStack()
            })
    }

    /**
     * InterBank Transfer Transfer Successful
     */
    composable<InterBankTransferRoutes.TransactionSuccessfulRoute> {
        val transactionDataJson: String? =
            it.toRoute<InterBankTransferRoutes.TransactionSuccessfulRoute>().json

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

