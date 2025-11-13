package dev.rajesh.mobile_banking.network

import io.ktor.client.engine.HttpClientEngine

expect class HttpClientEngineFactory(){
    fun getHttpEngine(): HttpClientEngine
}