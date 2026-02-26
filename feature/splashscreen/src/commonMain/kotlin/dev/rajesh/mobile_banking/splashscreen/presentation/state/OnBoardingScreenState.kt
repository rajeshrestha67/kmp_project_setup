package dev.rajesh.mobile_banking.splashscreen.presentation.state

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.splashscreen.presentation.model.Indicator
import dev.rajesh.mobile_banking.splashscreen.presentation.model.OnBoardingScreenItem
import dev.rajesh.mobile_banking.splashscreen.presentation.model.OnBoardingScreenItemList
import org.jetbrains.compose.resources.StringResource

data class OnBoardingScreenState(
    val currentPage: Int = 0,
    val screens: List<OnBoardingScreenItem> = OnBoardingScreenItemList.screenList,
    val title: StringResource = SharedRes.Strings.next
) {
    val indicators: List<Indicator>
        @Composable get() = screens.mapIndexed { index, _ ->
            val isSelected = index == currentPage
            Indicator(
                color = if (isSelected) MaterialTheme.appColors.onBoardingIndicatorSelectedColor else MaterialTheme.appColors.onBoardingIndicatorUnSelectedColor,
                width = if (isSelected) MaterialTheme.dimens.onBoardingIndicatorSelected else MaterialTheme.dimens.onBoardingIndicatorUnSelected,
            )
        }
}