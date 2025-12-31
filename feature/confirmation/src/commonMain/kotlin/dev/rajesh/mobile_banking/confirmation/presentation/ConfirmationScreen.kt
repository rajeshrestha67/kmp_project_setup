package dev.rajesh.mobile_banking.confirmation.presentation

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.button.AppButton
import dev.rajesh.mobile_banking.components.button.OutlinedAppButton
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationData
import dev.rajesh.mobile_banking.confirmation.model.ConfirmationItem
import dev.rajesh.mobile_banking.logger.AppLogger
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationScreen(
    data: ConfirmationData,
    onConfirm: () -> Unit,
    onBackClicked: () -> Unit
) {
    AppLogger.e("Confirmation screen", "I am here")
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
                        text = "Confirmation",
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
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small3)) {
                /*Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = data.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.appColors.primaryTextColor
                    )
                )*/
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = data.message,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.appColors.primaryTextColor
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )

                data.items.forEach {
                    ConfirmationRow(it)
                }

                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )

                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onConfirm,
//                    text = stringResource(SharedRes.Strings.confirm)
                    text = "Confirm"
                )

                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )

                OutlinedAppButton(
                    text = "Cancel",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onBackClicked,
                    textColor = Color.Red,
                    border = BorderStroke(1.dp, Color.Red),
                )
            }
        }
    }
}

@Composable
fun ConfirmationRow(it: ConfirmationItem) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.small3)
                .fillMaxWidth()
                .weight(1f),
            text = it.label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.appColors.secondaryTextColor
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.small3)
                .weight(1f),
            text = it.value,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.appColors.primaryTextColor
            )
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {


    MaterialTheme{
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text("hello")
        }
    }

}



