package dev.rajesh.mobile_banking.home.navigation
//
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.navigation
//import dev.rajesh.mobile_banking.banktransfer.BankTransferScreen
//import dev.rajesh.mobile_banking.banktransfer.ExternalBankTransferScreen
//import dev.rajesh.mobile_banking.banktransfer.FavouriteAccountsScreen
//import dev.rajesh.mobile_banking.banktransfer.InternalBankTransferScreen
//import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferOption
//import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferRoute
//import dev.rajesh.mobile_banking.home.HomeScreen
//
//fun NavGraphBuilder.homeNavGraph(navController: NavController) {
//    composable(route = "home") { //home --> same as DashboardRoute.HomeRoute.route
//        HomeScreen(navController = navController)
//    }
//
//    //bankTransfer Feature (nested navigation)
//    navigation(
//        startDestination = BankTransferRoute.root,
//        route = "home/bank_transfer" // Use a unique route path
//    ) {
//        composable(BankTransferRoute.root) {
//            BankTransferScreen(
//                onOptionSelected = { option ->
//                    when (option) {
//                        BankTransferOption.SAME_BANK ->
//                            navController.navigate(BankTransferRoute.sameBank)
//                        BankTransferOption.OTHER_BANK ->
//                            navController.navigate(BankTransferRoute.otherBank)
//                        BankTransferOption.FAVOURITE_ACCOUNTS ->
//                            navController.navigate(BankTransferRoute.favouriteAccounts)
//                    }
//                }
//            )
//        }
//
//        composable(BankTransferRoute.sameBank) {
//            InternalBankTransferScreen()
//        }
//
//        composable(BankTransferRoute.otherBank){
//            ExternalBankTransferScreen()
//        }
//
//        composable(route =BankTransferRoute.favouriteAccounts) {
//            FavouriteAccountsScreen()
//        }
//    }
//}