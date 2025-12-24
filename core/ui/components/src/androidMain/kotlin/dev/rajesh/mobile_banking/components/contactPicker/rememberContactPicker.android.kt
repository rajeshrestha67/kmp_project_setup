package dev.rajesh.mobile_banking.components.contactPicker

import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberContactPicker(
    onContactPicked: (String) -> Unit,
    onError: (Throwable) -> Unit
): () -> Unit {
    val context = LocalContext.current

    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { contactUri ->
        if (contactUri == null) {
            return@rememberLauncherForActivityResult
        } else {
            try {
                val contentResolver = context.contentResolver
                var contactId: String? = null
                var hasPhoneNumber: Int = 0

                // First, query the contact URI to get the Contact ID and whether it has a phone number
                val cursor = contentResolver.query(
                    contactUri,
                    null,
                    null,
                    null,
                    null)
                if (cursor?.moveToFirst() == true) {
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val hasPhoneNumberStr =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    if (hasPhoneNumberStr != null) {
                        hasPhoneNumber = hasPhoneNumberStr.toInt()
                    }
                }
                cursor?.close()

                // If the contact has a phone number, query the Phone content provider for it.
                if (hasPhoneNumber > 0 && contactId != null) {
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                        arrayOf(contactId),
                        null
                    )

                    // Get the first phone number found.
                    if (phoneCursor?.moveToFirst() == true) {
                        val phoneNumber =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        onContactPicked(phoneNumber)
                    }
                    phoneCursor?.close()
                } else {
                    onError(Exception("Selected contact does not have a phone number."))
                }
            } catch (e: Exception) {
                onError(e)
            }
        }

    }

    return remember {
        {
            contactPickerLauncher.launch(null)
        }
    }

}