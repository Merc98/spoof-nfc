package com.nfsp00f33r.app.hardware

import android.app.Activity
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import timber.log.Timber

/**
 * Handles USB attach intents declared in the manifest and forwards the selected
 * PN532-compatible device to the hardware layer.
 */
class UsbDeviceActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleUsbAttachIntent(intent)
        finish()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleUsbAttachIntent(intent)
        finish()
    }

    private fun handleUsbAttachIntent(intent: Intent?) {
        if (intent?.action != UsbManager.ACTION_USB_DEVICE_ATTACHED) {
            return
        }

        val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
        if (device == null) {
            Timber.w("USB attach intent received without device payload")
            return
        }

        Timber.i(
            "USB device attached: vendorId=%d productId=%d deviceName=%s",
            device.vendorId,
            device.productId,
            device.deviceName
        )
    }
}
