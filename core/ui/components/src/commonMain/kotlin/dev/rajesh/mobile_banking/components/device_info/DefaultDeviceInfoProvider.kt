package dev.rajesh.mobile_banking.components.device_info

import dev.rajesh.mobile_banking.model.device_info.DeviceInfo

object DefaultDeviceInfoProvider : DeviceInfoProvider {
    //override fun getDeviceInfo(): DeviceInfo = getDeviceInfo() // delegates to expect/actual

    override fun fetchDeviceInfo(): DeviceInfo {
        return getDeviceInfo()
    }
}