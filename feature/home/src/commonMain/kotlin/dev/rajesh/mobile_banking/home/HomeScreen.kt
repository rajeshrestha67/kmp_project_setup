package dev.rajesh.mobile_banking.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rajesh.mobile_banking.components.ProfilePicture
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.home.presentation.HomeScreenState
import dev.rajesh.mobile_banking.home.presentation.HomeScreenViewModel
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.utils.extractInitials
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val viewModel: HomeScreenViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.appColors.backgroundColor,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                windowInsets = WindowInsets(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.appColors.backgroundColor
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ProfilePicture(
                            imageUrl = "",
                            employeeName = state.fullName,
                            nameInitials = state.fullName.extractInitials(),
                            size = MaterialTheme.dimens.medium3,
                            shape = CircleShape,
                            background = MaterialTheme.appColors.imageBackgroundColor,
                            borderWidth = 0.5.dp,
                            borderColor = MaterialTheme.colorScheme.outline,
                            ratio = 1f
                        )
                        Column(
                            modifier = Modifier
                                .padding(horizontal = MaterialTheme.dimens.small2)
                                .align(alignment = Alignment.CenterVertically)
                        ) {
                            Text(
                                text = stringResource(state.greetingMsg),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.appColors.primaryTextColor
                                )
                            )
                            Text(
                                text = state.fullName,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                },
                actions = {

                    Row {
                        IconButton(
                            onClick = {

                            }) {
                            Icon(
                                //imageVector = Icons.AutoMirrored.Filled.Chat,
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(SharedRes.Icons.ic_notification),
                                contentDescription = "logout"
                            )
                        }

                        IconButton(
                            onClick = {

                            }) {
                            Icon(
                                //imageVector = Icons.AutoMirrored.Filled.Chat,
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(SharedRes.Icons.ic_qr_scanner),
                                contentDescription = "logout"
                            )
                        }
                    }
                }

            )
        }
    ) { contentPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            isRefreshing = false,
            onRefresh = {

            },
            content = {
                HomeScreenContent(state = state)
            }

        )
    }

}

@Composable
fun HomeScreenContent(state: HomeScreenState) {
    val mainListState = rememberLazyListState()
    val isScrolling by remember {
        derivedStateOf {
            mainListState.isScrollInProgress
        }
    }

    val isAtTop by remember {
        derivedStateOf {
            mainListState.firstVisibleItemIndex == 0 && mainListState.firstVisibleItemScrollOffset == 0
        }
    }
    val isAtEnd by remember {
        derivedStateOf {
            val lastVisibleItem = mainListState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItemsCount = mainListState.layoutInfo.totalItemsCount

            lastVisibleItem != null && lastVisibleItem.index == totalItemsCount - 1
        }
    }

    val shouldShowSwipeToDismiss by remember(state.showSwipeView) {
        derivedStateOf { (!isScrolling || isAtTop || isAtEnd) && state.showSwipeView }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.appColors.backgroundColor),
        state = mainListState,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            top = MaterialTheme.dimens.small2,
            bottom = MaterialTheme.dimens.swipeToDismissHeight + MaterialTheme.dimens.bottomBar
        )
    ) {
        userDetailCard(state = state)
    }
}


fun LazyListScope.userDetailCard(state: HomeScreenState) {
    item(key = "userDetails") {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth().padding(MaterialTheme.dimens.small3),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.appColors.primaryColor
            ),
            shape = RoundedCornerShape(MaterialTheme.dimens.cardCornerRadius)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small3)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row() {
                        Text(
                            text = stringResource(SharedRes.Strings.a_c_no),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.appColors.onPrimary,
                                fontSize = MaterialTheme.dimens.smallFontSize
                            )
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1),
                            text = state.accountNumber,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.appColors.onPrimary,
                                fontSize = MaterialTheme.dimens.smallFontSize
                            )
                        )

                    }
                    AddMoneyView()
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                Text(
                    text = state.accountName,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.appColors.onPrimary,
                        fontSize = MaterialTheme.dimens.smallFontSize
                    )
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = stringResource(SharedRes.Strings.actualBalance),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.appColors.onPrimary,
                                fontSize = MaterialTheme.dimens.smallFontSize
                            )
                        )
                        Text(
                            text = stringResource(SharedRes.Strings.currencyType) + " " + "8,50,000",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.appColors.onPrimary,
                                fontSize = MaterialTheme.dimens.smallFontSize

                            )
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(SharedRes.Strings.availableBalance),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.appColors.onPrimary,
                                fontSize = MaterialTheme.dimens.smallFontSize
                            )
                        )
                        Text(
                            text = stringResource(SharedRes.Strings.currencyType) + " " + "8,45,000",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.appColors.onPrimary,
                                fontSize = MaterialTheme.dimens.smallFontSize

                            )
                        )
                    }
                }

            }

        }
    }


}

@Composable
fun AddMoneyView() {
    Surface(
        shape = RoundedCornerShape(MaterialTheme.dimens.cardCornerRadius),
        color = MaterialTheme.appColors.backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Money",
                tint = MaterialTheme.appColors.primaryColor,
                modifier = Modifier.size(16.dp)
            )
            Text(
                "Add Money", style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.appColors.primaryColor,
                    fontSize = MaterialTheme.dimens.smallFontSize
                )
            )
        }
    }

}