package dev.rajesh.mobile_banking.paymentAuthentication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun NumericKeyPad(
    onDigitClick: (Int) -> Unit,
    onOkClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val keys = listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),
        listOf(-1, 0, -2)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        keys.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { key ->
                    when (key) {
                        -1 -> {
                            KeypadButton(
                                text = "⌫",
                                onClick = onDeleteClick
                            )
                        }

                        -2 -> {
                            KeypadButton(
                                text = "✓",
                                onClick = onOkClick
                            )
                        }

                        else -> {
                            KeypadButton(
                                text = key.toString(),
                                onClick = { onDigitClick(key) }
                            )
                        }
                    }
                }
            }
        }
    }
}