package dev.rajesh.mobile_banking.components.contactPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.ContactsUI.CNContactPickerViewController
import platform.UIKit.UIApplication
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

@Composable
actual fun rememberContactPicker(
    onContactPicked: (String) -> Unit,
    onError: (Throwable) -> Unit
): () -> Unit {

    val contactPickerDelegate = remember { ContactPickerDelegate(onContactPicked, onError) }

    return remember {
        {
            dispatch_async(dispatch_get_main_queue()) {
                try {
                    val picker = CNContactPickerViewController()
                    picker.delegate = contactPickerDelegate

                    val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
                    rootVC?.presentViewController(picker, animated = true, completion = null)
                } catch (e: Throwable) {
                    onError(e)
                }
            }
        }
    }
}