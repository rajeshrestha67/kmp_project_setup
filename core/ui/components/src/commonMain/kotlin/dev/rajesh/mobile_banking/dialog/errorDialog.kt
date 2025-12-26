import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.rajesh.mobile_banking.components.appColors
import dev.rajesh.mobile_banking.components.dimens

/*@Composable
fun ErrorDialog(
    state: DialogState,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = state.title) },
        text = { Text(text = state.description) },
        confirmButton = {
            Button(onClick = {
                state.onConfirm()
                onDismiss()
            }) {
                Text("OK")
            }
        }
    )
}*/

@Composable
fun ErrorDialog(
    title: String,
    msg: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().height(300.dp).padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.appColors.backgroundColor
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small3),
                    thickness = 1.dp,
                    color = MaterialTheme.appColors.primaryTextColor //black on light, white on dark
                )
                Text(
                    text = msg,
                    modifier = Modifier.padding(16.dp).weight(weight = 1.0f),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.appColors.primaryTextColor
                    )
                )
                Button(onClick = onDismiss) {
                    Text("Dismiss")
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
            }
        }
    }
}