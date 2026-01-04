package dev.rajesh.mobile_banking.components.contactPicker

import dev.rajesh.mobile_banking.logger.AppLogger
import platform.Contacts.CNContact
import platform.Contacts.CNLabeledValue
import platform.Contacts.CNPhoneNumber
import platform.ContactsUI.CNContactPickerDelegateProtocol
import platform.ContactsUI.CNContactPickerViewController
import platform.darwin.NSObject

class ContactPickerDelegate(
    private val onContactPicked: (String) -> Unit,
    private val onError: (Throwable) -> Unit
) : NSObject(), CNContactPickerDelegateProtocol {

    override fun contactPicker(picker: CNContactPickerViewController, didSelectContact: CNContact) {
        try {
            val labeledValue = didSelectContact.phoneNumbers.firstOrNull() as? CNLabeledValue
            val cnPhoneNumber = labeledValue?.value as? CNPhoneNumber
            val phoneNumber = cnPhoneNumber?.stringValue
            AppLogger.i("Contact picker Ios", "mobile number selected: $phoneNumber")
            if (phoneNumber != null) {
                onContactPicked(phoneNumber)
            } else {
                onError(Exception("Selected contact has no phone number"))
            }
        } catch (e: Throwable) {
            onError(e)
        }
    }

    // Called when user cancels
    override fun contactPickerDidCancel(picker: CNContactPickerViewController) {
        AppLogger.i("Contact picker Ios", "Contact picker was cancelled by the user.")
    }
}