package com.example.naariraksha.util

import android.telephony.SmsManager

object SmsUtil {

    fun sendSms(phone: String, message: String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            phone,
            null,
            message,
            null,
            null
        )
    }
}


