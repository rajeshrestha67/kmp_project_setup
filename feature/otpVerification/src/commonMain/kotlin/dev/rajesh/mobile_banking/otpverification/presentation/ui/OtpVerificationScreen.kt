package dev.rajesh.mobile_banking.otpverification.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.button.AppButton
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.otpverification.presentation.components.OtpInputField
import dev.rajesh.mobile_banking.otpverification.presentation.state.OtpEffect
import dev.rajesh.mobile_banking.otpverification.presentation.state.OtpEvent
import dev.rajesh.mobile_banking.otpverification.presentation.viewmodel.OtpVerificationViewModel
import dev.rajesh.mobile_banking.res.SharedRes
import kotlinx.coroutines.flow.SharedFlow
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    otpViewModel: OtpVerificationViewModel = koinViewModel(),
    otpEffect: SharedFlow<OtpEffect>,
    onVerifyClicked: (String) -> Unit,
    onResendClicked: ()-> Unit,
    onSuccess: () -> Unit,
    onBackClicked: () -> Unit
) {

    val state by otpViewModel.state.collectAsState()

    // Listen for effects from the Parent ViewModel
    LaunchedEffect(Unit) {
        otpEffect.collect { effect ->
            when (effect) {
                is OtpEffect.OtpError -> {
                    otpViewModel.setApiError(effect.message)
                }

                is OtpEffect.VerificationSuccess -> {
                    onSuccess()
                }

                else -> {}
            }
        }
    }

    LaunchedEffect(Unit) {
        otpViewModel.events.collect { event ->
            when (event) {
                is OtpEvent.VerifyClicked -> {
                    onVerifyClicked(event.otp)
                }
                OtpEvent.ResendClicked -> {
                    onResendClicked()
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
                        text = "OTP Verification",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.appColors.primaryTextColor
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClicked,
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "back arrow"
                            )
                        }
                    )
                }

            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(contentPadding)
                .padding(horizontal = MaterialTheme.dimens.small3),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth()
                    .height(150.dp),
                model = SharedRes.getRes("drawable/img_otp_verification_background.png"),
                contentDescription = "otp verification",
            )

            Text(
                text = "Enter Your \nVerification Code",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.appColors.primaryTextColor,
                    fontWeight = FontWeight.W800
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))
            Text(
                text = "Otp has been sent to your mobile number",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.appColors.primaryTextColor
                )
            )

            Spacer(Modifier.height(MaterialTheme.dimens.small3))

            OtpInputField(
                otp = state.otp,
                otpLength = state.otpLength,
                onOtpChange = otpViewModel::onOtpChange
            )

            if (state.error != null && state.error.toString().isNotEmpty()) {
                Spacer(Modifier.height(MaterialTheme.dimens.small3))
                Text(
                    text = state.error.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.appColors.lightRedColor
                    )
                )
            }
            Spacer(Modifier.height(MaterialTheme.dimens.small3))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Didn't get it?",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.appColors.primaryTextColor
                    )
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.small3)
                        .border(
                            1.dp,
                            if (state.canResend) {
                                MaterialTheme.appColors.primaryColor
                            } else {
                                MaterialTheme.appColors.disabledTextFieldBorderColor
                            },
                            RoundedCornerShape(32.dp),
                        )
                        .padding(horizontal = MaterialTheme.dimens.small3, vertical = 8.dp)
                        .clickable {
                            if (state.canResend) {
                                otpViewModel.resendOtp()
                            }
                        },
                    text = "Resend Code",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (state.canResend) {
                            MaterialTheme.appColors.primaryTextColor
                        } else {
                            MaterialTheme.appColors.disabledTextFieldBorderColor
                        }
                    )
                )

                Text(
                    text = "00:${state.timerSeconds.toString().padStart(2, '0')}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.appColors.primaryColor
                    )
                )
            }
            Spacer(Modifier.height(32.dp))

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    //onVerifyClicked(state.otp)
                    otpViewModel.verifyOtp()
                },
                enabled = state.otp.length == state.otpLength,
                text = "Verify OTP"
            )

        }

    }

}