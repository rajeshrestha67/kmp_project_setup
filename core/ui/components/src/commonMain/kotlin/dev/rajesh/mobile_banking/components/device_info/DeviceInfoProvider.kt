package dev.rajesh.mobile_banking.components.device_info

import dev.rajesh.mobile_banking.model.device_info.DeviceInfo

interface DeviceInfoProvider {
    fun fetchDeviceInfo(): DeviceInfo
}