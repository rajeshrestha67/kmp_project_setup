package dev.rajesh.mobile_banking.home.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuickServiceDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val uniqueIdentifier: String,
    val isNew: Boolean,
    val appOrder: Int,
    val services: List<Service>
)

@Serializable
data class Service(
    val id: Int,
    val url: String,
    val uniqueIdentifier: String,
    val service: String,
    val status: String,
    val labelName: String,
    val labelSize: String,
    val labelSample: String,
    val labelPrefix: String,
    val instructions: String,
    val fixedlabelSize: Boolean,
    val priceInput: Boolean,
    val notificationUrl: String,
    val minValue: Double,
    val maxValue: Double,
    val icon: String,
    val categoryId: Int,
    val serviceCategoryName: String,
    val webView: Boolean,
    val isNew: Boolean,
    val cashBackView : String,
    val appOrder: Int,
    val isSmsMode: Boolean,
    val ticketStatus: Boolean,
    val labelMaxLength: String,
    val labelMinLength: String,
    val priceRange: String
)