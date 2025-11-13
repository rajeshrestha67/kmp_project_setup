package com.gurkha.hr.logger.remote

import dev.rajesh.mobile_banking.logger.LogEntry


class KtorRemoteLogger : RemoteLogger {
    override suspend fun sendLogs(logs: List<LogEntry>) {
        println("logs $logs")
    }
}