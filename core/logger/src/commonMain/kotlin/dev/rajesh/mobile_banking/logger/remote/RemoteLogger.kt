package dev.rajesh.mobile_banking.logger.remote

import dev.rajesh.mobile_banking.logger.LogEntry


interface RemoteLogger {
    suspend fun sendLogs(logs: List<LogEntry>)
}