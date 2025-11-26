package dev.rajesh.mobile_banking.logger.remote

import dev.rajesh.mobile_banking.logger.LogEntry
import dev.rajesh.mobile_banking.logger.remote.RemoteLogger


class KtorRemoteLogger : RemoteLogger {
    override suspend fun sendLogs(logs: List<LogEntry>) {
        println("logs $logs")
    }
}