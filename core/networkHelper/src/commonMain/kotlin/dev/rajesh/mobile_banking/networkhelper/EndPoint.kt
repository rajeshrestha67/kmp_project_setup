package dev.rajesh.mobile_banking.networkhelper

object EndPoint {
    const val LOGIN = "oauth/token"
    const val CUSTOMER_DETAILS = "/api/customerdetails"
    const val BANKING_SERVICES = "/appServiceManagement/appServices/bank/app"
    const val QUICK_SERVICES = "/api/category"
    const val COOP_BRANCH = "/get/bankbranches"
    const val VALIDATE_ACCOUNT = "/api/account/validation"
    const val FUND_TRANSFER = "/api/fundtransfer"

    const val SCHEME_CHARGE = "/api/ips/scheme/charge"

    const val BANK_LIST = "/api/ips/bank"

    const val INTER_BANK_VALIDATION = "/api/account/validation"

    const val IPS_TRANSFER = "/api/ips/transfer"
    const val WALLET_LIST = "api/wallet/list"
    const val WALLET_VALIDATION = "/api/walletvalidate"
    const val WALLET_SERVICE_CHARGE = "api/services/charge/get"
    const val WALLET_LOAD = "/api/wallet/load"

}