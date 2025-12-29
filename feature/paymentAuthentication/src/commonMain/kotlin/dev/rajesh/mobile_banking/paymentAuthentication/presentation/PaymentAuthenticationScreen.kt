package dev.rajesh.mobile_banking.paymentAuthentication.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.rajesh.mobile_banking.components.PlatformMessage
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.components.MPinOutlinedField
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.components.NumericKeyPad
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.state.PaymentAuthAction
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.state.PaymentAuthEffect
import dev.rajesh.mobile_banking.paymentAuthentication.presentation.viewModel.PaymentAuthViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentAuthenticationScreen(
    onMPinVerified: (String) -> Unit,
    onBackClicked: () -> Unit
) {

    val viewModel: PaymentAuthViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val platformMessage: PlatformMessage = koinInject()

    LaunchedEffect(Unit) {
        viewModel.errorChannel.collect {
            platformMessage.showToast(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.paymentAuthEffect.collect {
            when (it) {
                is PaymentAuthEffect.mPinAuthenticated -> {
                    onMPinVerified(it.mPin)
                }
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
                    Text(
                        "Verify",
                        style = MaterialTheme.typography.titleMedium
                            .copy(color = MaterialTheme.appColors.primaryTextColor)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClicked,
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back arrow"
                            )
                        },
                    )
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.small3),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Please verify mPin to make payment",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(24.dp))

                //MPinIndicator(mPinLength = state.mPin.length)
                MPinOutlinedField(
                    state.mPin,
                    state.mPinFromDS.length,
                    state.isMpinVisible,
                    onToggleVisibility = {
                        viewModel.onAction(PaymentAuthAction.ToggleVisibility)
                    }
                )

                Spacer(Modifier.height(16.dp))

                state.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                NumericKeyPad(
                    onDigitClick = {
                        viewModel.onAction(PaymentAuthAction.OnDigitClick(it))
                    },
                    onOkClick = {
                        //onMPinVerified(state.mPin)
                        viewModel.onAction(PaymentAuthAction.OnConfirm)

                    },
                    onDeleteClick = {
                        viewModel.onAction(PaymentAuthAction.OnDelete)
                    },
                )

                Spacer(Modifier.height(16.dp))

                Row {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Chat,
                        tint = Color.Red,
                        contentDescription = "Biometric",
                    )
                    Text(
                        "Verify using biometric",
                        modifier = Modifier.padding(4.dp).clickable {
                            //onBackClicked()
                        },
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.appColors.primaryTextColor
                        )

                    )
                }

                Spacer(Modifier.height(16.dp))


                Text(
                    "Back",
                    modifier = Modifier.padding(4.dp).clickable {
                        onBackClicked()
                    },
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Red
                    )

                )
            }
        }

    }

}
