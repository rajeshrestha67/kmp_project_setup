package dev.rajesh.mobile_banking.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.kmpnotifier.notification.NotifierManager
import dev.rajesh.mobile_banking.logger.AppLogger
import kotlinx.coroutines.launch

class PushNotificationViewModel() : ViewModel() {

    init {
        register()
    }

    fun register() {
        AppLogger.e("PushNotificationViewModel", "Registering PushToken")
        viewModelScope.launch {
            try {
                val token = NotifierManager.getPushNotifier().getToken()
                if (token != null) {
                    AppLogger.e("PushNotificationViewModel", "PushToken: $token")
                    syncTokenWithBackend(token)
                } else {
                    AppLogger.e(
                        "PushNotificationViewModel",
                        "Token is still null, waiting for onNewToken..."
                    )
                }
            } catch (e: Exception) {
                AppLogger.e("PushNotificationViewModel", "Error fetching token: ${e.message}")
            }
        }
    }


    private fun syncTokenWithBackend(token: String) {
        println("Syncing token to server: $token")
    }
}