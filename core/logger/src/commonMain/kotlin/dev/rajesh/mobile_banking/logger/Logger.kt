package dev.rajesh.mobile_banking.logger

import dev.rajesh.mobile_banking.model.network.DataError


interface Logger {
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String, error: DataError? = null)
    fun e(tag: String, message: String, error: DataError? = null)
}