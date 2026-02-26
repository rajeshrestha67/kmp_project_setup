package dev.rajesh.mobile_banking.splashscreen.presentation.model

import dev.rajesh.mobile_banking.res.SharedRes

data class OnBoardingScreenItem(
    val title: String,
    val description: String,
    val image: String
)

object OnBoardingScreenItemList {
    val screenList = listOf(
        OnBoardingScreenItem(
            title = "Welcome to eBanking Sewa \n Access your account through your mobile phone securely with our Mobile Banking platform.",
            description = "Take command of your bank account, discover faster yet the simplest way to banking with our mobile banking app.",
            image = SharedRes.getRes("drawable/onboarding1.png")
        ),
        OnBoardingScreenItem(
            title = "Anyone, Anywhere, Anytime \n Access your account through your mobile phone securely with our Mobile Banking platform.",
            description = "Take command of your bank account, discover faster yet the simplest way to banking with our mobile banking app.",
            image = SharedRes.getRes("drawable/onboarding1.png")
        ),
        OnBoardingScreenItem(
            title = "Track your transaction \n Access your account through your mobile phone securely with our Mobile Banking platform.",
            description = "Take command of your bank account, discover faster yet the simplest way to banking with our mobile banking app.",
            image = SharedRes.getRes("drawable/onboarding1.png")
        ),


        )
}