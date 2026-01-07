package dev.rajesh.mobile_banking.otpverification.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.rajesh.mobile_banking.components.appColors

@Composable
fun OtpInputField(
    otp: String,
    otpLength: Int = 6,
    onOtpChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { focusRequester.requestFocus() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(otpLength) { index ->
                val char = otp.getOrNull(index)?.toString() ?: ""

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            width = 2.dp,
                            color = if (isFocused) MaterialTheme.appColors.primaryColor
                            else MaterialTheme.appColors.disabledTextFieldBorderColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(
                        text = char,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        // Hidden TextField
        BasicTextField(
            value = otp,
            onValueChange = { input ->
                val digits = input.filter { it.isDigit() }
                when {
                    digits.length > otp.length -> {
                        if (digits.length <= otpLength) {
                            onOtpChange(digits)
                        }
                    }

                    digits.length < otp.length -> {
                        onOtpChange(digits)
                    }

                    else -> {
                        // same length → ignore (cannot delete from middle)
                    }
                }
            },
            keyboardOptions = keyboardOptions,
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.appColors.backgroundColor),
            textStyle = TextStyle(
                color = Color.Transparent,
                fontSize = 1.sp        // almost zero size text
            ),
            modifier = Modifier
                .matchParentSize()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                .alpha(0f)//COMPLETELY HIDE DRAWING

        )
    }

    // Request focus initially
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
