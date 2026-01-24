package com.example.naariraksha.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock

class PowerButtonReceiver(
    private val onTriplePress: () -> Unit
) : BroadcastReceiver() {

    private var pressCount = 0
    private var lastPressTime = 0L

    override fun onReceive(context: Context?, intent: Intent?) {

        val currentTime = SystemClock.elapsedRealtime()

        if (currentTime - lastPressTime < 1500) {
            pressCount++
        } else {
            pressCount = 1
        }

        lastPressTime = currentTime

        if (pressCount == 3) {
            pressCount = 0
            onTriplePress()
        }
    }
}
