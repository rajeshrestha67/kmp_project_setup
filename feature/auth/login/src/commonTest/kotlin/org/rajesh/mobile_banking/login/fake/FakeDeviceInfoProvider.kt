package org.rajesh.mobile_banking.login.fake

import dev.rajesh.mobile_banking.components.device_info.DeviceInfoProvider
import dev.rajesh.mobile_banking.model.device_info.DeviceInfo


class FakeDeviceInfoProvider(
    private val deviceInfo: DeviceInfo = DeviceInfo(
        deviceUniqueIdentifier = "test-id",
        platform = "Test",
        manufacturer = "TestManu",
        model = "TestModel",
        osVersion = "TestOS",
        sdkInt = "0",
        locale = "en_US",
        timezone = "UTC",
        appVersion = "0.0.0",
        appBuild = "0",
        deviceName = "TestDevice",
        isEmulator = false
    )
) : DeviceInfoProvider {
    override fun fetchDeviceInfo(): DeviceInfo = deviceInfo
}