package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.text.input.ImeAction
import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.MobileTextField
import dev.rajesh.mobile_banking.res.SharedRes
import org.jetbrains.compose.resources.stringResource

@Composable
fun MobileNumberField (
    value: String,
    onValueChanged: (String)-> Unit,
    onPickContactClicked:()-> Unit
){
    MobileTextField(
        modifier = Modifier.fillMaxWidth(), //.focusRequester(focusRequester),
        label = stringResource(SharedRes.Strings.mobileNumber),
        hint = "",
        value = value,
        onValueChange = {},
        onErrorStateChange = {},
        rules = FormValidate.mobileNumberValidationRules,
        imeAction = ImeAction.Next,
//        keyboardActions = KeyboardActions(
//            onNext = {
//                focusManager.moveFocus(FocusDirection.Down)
//            }
//        )
        trailingIcon = {
            IconButton(onClick = onPickContactClicked){
                Icon(
                    imageVector = Icons.Default.Contacts,
                    contentDescription = "Pick Contact"
                )
            }
        }

    )
}