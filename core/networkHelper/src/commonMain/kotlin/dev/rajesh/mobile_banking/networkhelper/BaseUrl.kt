package dev.rajesh.mobile_banking.networkhelper


sealed class BaseUrl(open val url: String) {

    data object Url : BaseUrl("mbank.com.np")

}