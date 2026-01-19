package dev.rajesh.mobile_banking.qrscanner.presentation.ui

import ErrorDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.components.loadingScreen.LoadingScreen
import dev.rajesh.mobile_banking.components.media.checkGalleryFullAccess
import dev.rajesh.mobile_banking.components.media.checkGalleryLimitAccess
import dev.rajesh.mobile_banking.components.media.rememberGalleryLauncher
import dev.rajesh.mobile_banking.components.permissions.CAMERA_PERMISSION
import dev.rajesh.mobile_banking.components.permissions.GALLERY_PERMISSION
import dev.rajesh.mobile_banking.components.permissions.GALLERY_PERMISSION_LIMITED
import dev.rajesh.mobile_banking.components.permissions.rememberRequestPermission
import dev.rajesh.mobile_banking.loadWallet.domain.model.QrWallet
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.qrscanner.domain.model.AccountDetails
import dev.rajesh.mobile_banking.qrscanner.presentation.components.QrScannerView
import dev.rajesh.mobile_banking.qrscanner.presentation.state.QrNavigationEffect
import dev.rajesh.mobile_banking.qrscanner.presentation.state.QrScannerScreenAction
import dev.rajesh.mobile_banking.qrscanner.presentation.viewmodel.QrScannerViewModel
import dev.rajesh.mobile_banking.res.SharedRes
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

private const val TAG = "QrScannerScreen"

@Composable
fun QrScannerScreen(
    toInterBankTransfer: (AccountDetails) -> Unit,
    toSameBankTransfer: (AccountDetails) -> Unit,
    toWallet: (walletDetail: WalletDetail, walletUserId: String, walletHolderName: String) -> Unit,
    onBackClicked: () -> Boolean
) {

    val viewModel: QrScannerViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    val isScannerPaused = state.errorData != null

    val cameraPermission = rememberRequestPermission(
        permissions = listOf(
            CAMERA_PERMISSION
        ),
        onGranted = { permission ->
            AppLogger.i(tag = TAG, message = "Permission Granted: $permission")
        },

        onDenied = { permission ->
            AppLogger.i(tag = TAG, message = "Permission denied: $permission")
        },
        onPermanentlyDenied = { permission ->
            AppLogger.i(tag = TAG, message = "Permission permanently: $permission")
        },
        onAllGranted = {
            AppLogger.i(tag = TAG, message = "All Permission granted")
        }

    )

    val openGallery = rememberGalleryLauncher(
        onImageSelected = { uri ->
            AppLogger.i(
                tag = TAG,
                message = "Image selected from gallery: $uri"
            )
            viewModel.onAction(QrScannerScreenAction.OnQrImageSelectedFromGallery(uri))
        },
        onError = {
            AppLogger.e(tag = TAG, message = "Error on gallery launcher")
        }
    )

    val onGalleryPermission = rememberRequestPermission(
        permissions = listOf(
            GALLERY_PERMISSION,
            GALLERY_PERMISSION_LIMITED
        ).filter { it.isNotEmpty() },
        onGranted = {
            AppLogger.i(tag = TAG, message = "permission Granted: $it")
            if (it == GALLERY_PERMISSION) {
                openGallery()
            }
        },
        onDenied = {
            AppLogger.i(tag = TAG, message = "permission denied: $it")
        },
        onPermanentlyDenied = {
            AppLogger.i(tag = TAG, message = "permission permanently denied: $it")
        },
        onAllGranted = {
            AppLogger.i(tag = TAG, message = "All Permission granted")
        }

    )

    LaunchedEffect(Unit) {
        cameraPermission()
    }

    LaunchedEffect(Unit) {
        val fullAccess = checkGalleryFullAccess()
        val limitedAccess = checkGalleryLimitAccess()

        if (!fullAccess && !limitedAccess) {
            onGalleryPermission()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is QrNavigationEffect.ToInterbankTransfer -> {
                    toInterBankTransfer(it.accountDetails)
                }

                is QrNavigationEffect.ToSameBankTransfer -> {
                    toSameBankTransfer(it.accountDetails)
                }

                is QrNavigationEffect.ToWallet -> {
                    toWallet(it.walletDetails, it.walletUserId, it.walletHolderName)
                }

                is QrNavigationEffect.ShowError -> {}
            }
        }
    }



    state.errorData?.let {
        ErrorDialog(
            title = "Error",
            msg = it.message.orEmpty(),
            onDismiss = {
                viewModel.dismissError()
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            QrScannerView(
                isPaused = isScannerPaused,
                onResult = {
                    AppLogger.i(TAG, "Qr Result: Success: $it")
                    viewModel.onAction(QrScannerScreenAction.OnQrScanned(it))
                },
                onError = {
                    AppLogger.i(TAG, "Qr Result: Error:  $it")
                    viewModel.onAction(QrScannerScreenAction.OnQrScanError("Error"))
                }
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { onBackClicked() },
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.small2)
                ) {
                    Icon(
                        painter = painterResource(SharedRes.Icons.ic_close),
                        modifier = Modifier.size(20.dp),
                        tint = Color.White,
                        contentDescription = "close icon"
                    )
                }

                IconButton(
                    onClick = onGalleryPermission,
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.small2)
                ) {
                    Icon(
                        //painter = painterResource(SharedRes.Icons.ic_close),
                        imageVector = Icons.Default.Image,
                        modifier = Modifier.size(20.dp),
                        tint = Color.White,
                        contentDescription = "close icon"
                    )
                }
            }

            if (state.isFetchingQrMerchantDetails) {
                LoadingScreen()
            }

        }

    }
}