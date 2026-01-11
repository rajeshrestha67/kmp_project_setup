package dev.rajesh.mobile_banking.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.rajesh.mobile_banking.banktransfer.navigation.BankTransferRoute
import dev.rajesh.mobile_banking.components.PlatformMessage
import dev.rajesh.mobile_banking.components.ProfilePicture
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.components.shimmer.ShimmerView
import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.presentation.HomeScreenActions
import dev.rajesh.mobile_banking.home.presentation.HomeScreenState
import dev.rajesh.mobile_banking.home.presentation.HomeScreenViewModel
import dev.rajesh.mobile_banking.loadWallet.presentation.navigation.LoadWalletRoute
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.qrscanner.navigation.QrScannerRoute
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.utils.extractInitials
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel: HomeScreenViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val platformMessage: PlatformMessage = koinInject()

    LaunchedEffect(Unit) {
        viewModel.actions.collect { action ->
            when (action) {
                is HomeScreenActions.OnBankingServiceClicked -> {
                    navigateToBankingFeature(
                        navController = navController,
                        service = action.service
                    )
                }

                is HomeScreenActions.OnQuickServiceClicked -> {
//                    navigateToQuickService(
//                        navController = navController,
//                        service = action.service
//                    )
                }

                is HomeScreenActions.OnQrScannerClicked -> {
                    navController.navigate(QrScannerRoute.root)
                }

                else -> Unit
            }
        }
    }

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
                                viewModel.onAction(HomeScreenActions.OnQrScannerClicked)
                            }) {
                            Icon(
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
                HomeScreenContent(state = state, viewModel = viewModel)
            }

        )
    }

}

@Composable
fun HomeScreenContent(
    state: HomeScreenState,
    viewModel: HomeScreenViewModel
) {
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
//        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3),
        horizontalAlignment = Alignment.CenterHorizontally,
//        contentPadding = PaddingValues(
//            bottom = MaterialTheme.dimens.swipeToDismissHeight + MaterialTheme.dimens.bottomBar
//        )
    ) {
        userDetailCard(state = state)
        bankingServiceList(
            isBankingServiceLoading = state.isBankingServiceLoading,
            bankingServicesList = state.bankingServicesList,
            viewModel = viewModel
        )
        quickServices(
            isQuickServicesLoading = state.isQuickServiceLoading,
            servicesList = state.quickServicesList
        )
    }
}


fun LazyListScope.userDetailCard(state: HomeScreenState) {
    item(key = "userDetails") {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.small3),
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
                            text = stringResource(SharedRes.Strings.currencyType) + " " + state.actualBalance,
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
                            text = stringResource(SharedRes.Strings.currencyType) + " " + state.availableBalance,
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

fun LazyListScope.bankingServiceList(
    isBankingServiceLoading: Boolean,
    bankingServicesList: List<BankingServiceDetail>,
    viewModel: HomeScreenViewModel
) {
    item {
        when {
            isBankingServiceLoading -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
                ) {
                    items(5) {
                        ShimmerView(
                            modifier = Modifier
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                                .size(width = 80.dp, height = 80.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                    }
                }
            }

            else -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                        .padding(MaterialTheme.dimens.small3),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
                ) {
                    items(bankingServicesList) { service ->
                        BankServiceCard(
                            service,
                            onClick = { item ->
                                AppLogger.i("HomeScreen", "Clicked: ${item.name}")
                                viewModel.onAction(HomeScreenActions.OnBankingServiceClicked(item))
                            })
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun BankServiceCard(
    service: BankingServiceDetail,
    onClick: (BankingServiceDetail) -> Unit
) {
    Card(
        modifier = Modifier
            .size(height = 80.dp, width = 80.dp)
            .padding(5.dp)
            .clickable {
                onClick(service)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.appColors.primaryContainerColor
        ),

        ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(MaterialTheme.dimens.small1),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = service.imageUrl,
                modifier = Modifier.size(30.dp),
                contentDescription = "Service Image",
                colorFilter = ColorFilter.tint(MaterialTheme.appColors.primaryColor)
            )
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1),
                text = service.name,
                fontSize = MaterialTheme.dimens.smallFontSize,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                minLines = 2
            )
        }
    }

}

fun LazyListScope.quickServices(
    isQuickServicesLoading: Boolean,
    servicesList: List<QuickServiceDetail>
) {
    item {
        Text(
            "Quick Services",
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.small3),
            textAlign = TextAlign.Left
        )
    }
    item {
        BoxWithConstraints(modifier = Modifier.padding(MaterialTheme.dimens.small3)) {
            val columnCount = 4
            val rows = (servicesList.size + 3) / columnCount
            val itemHeight = 80.dp
            val space = MaterialTheme.dimens.small2
            val gridHeight = rows * (itemHeight + space)
            LazyVerticalGrid(
                columns = GridCells.Fixed(columnCount),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gridHeight),
                horizontalArrangement = Arrangement.spacedBy(space),
                verticalArrangement = Arrangement.spacedBy(space),
                userScrollEnabled = false,
            ) {
                items(servicesList) { serviceItem ->
                    QuickServiceItem(serviceItem, onClick = { serviceItem ->
                        AppLogger.i("HomeScreen", "quick service Clicked: ${serviceItem.name}")
                    })
                }
            }
        }

    }
}

@Composable
fun QuickServiceItem(
    service: QuickServiceDetail,
    onClick: (QuickServiceDetail) -> Unit
) {
    Box(
        modifier = Modifier
            .size(height = 80.dp, width = 60.dp)
            .clickable {
                onClick(service)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(MaterialTheme.dimens.small1),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                modifier = Modifier.size(height = 25.dp, width = 25.dp),
                model = service.imageUrl,
                contentDescription = "serviceImage",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Text(
                minLines = 2,
                text = service.name,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = MaterialTheme.dimens.smallFontSize,
                    color = MaterialTheme.appColors.primaryTextColor
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

fun navigateToBankingFeature(navController: NavController, service: BankingServiceDetail) {
    when (service.uniqueIdentifier) {
        "bank_transfer" -> navController.navigate(BankTransferRoute.root)
        "load_wallet" -> navController.navigate(LoadWalletRoute.root)
    }
}


