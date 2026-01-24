package com.example.naariraksha

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.naariraksha.util.ShakeDetector
import com.example.naariraksha.util.SmsUtil
import com.example.naariraksha.util.PowerButtonReceiver
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.example.naariraksha.ui.FakeCallActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sensorManager: SensorManager
    private lateinit var shakeDetector: ShakeDetector
    private lateinit var powerButtonReceiver: PowerButtonReceiver

    private val permissionCode = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        val sosButton = findViewById<Button>(R.id.btnSOS)
        val fakeCallButton = findViewById<Button>(R.id.btnFakeCall)

        sosButton.setOnClickListener {
            if (hasAllPermissions()) {
                triggerSOS()
            } else {
                requestPermissions()
            }
        }

        // 🔹 STEP 9.5 — FAKE CALL BUTTON
        fakeCallButton.setOnClickListener {
            val intent = Intent(this, FakeCallActivity::class.java)
            startActivity(intent)
        }

        // 🔹 SHAKE DETECTOR
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        shakeDetector = ShakeDetector {
            if (hasAllPermissions()) {
                triggerSOS()
                Toast.makeText(
                    this,
                    "Shake detected! SOS triggered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // 🔹 POWER BUTTON RECEIVER
        powerButtonReceiver = PowerButtonReceiver {
            if (hasAllPermissions()) {
                triggerSOS()
                Toast.makeText(
                    this,
                    "Power button SOS triggered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // 🔴 GET LOCATION
    private fun triggerSOS() {

        if (!hasAllPermissions()) {
            requestPermissions()
            return
        }

        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    val message = if (location != null) {
                        "SOS! I need help.\nLocation: https://maps.google.com/?q=${location.latitude},${location.longitude}"
                    } else {
                        "SOS! I need help. Location unavailable."
                    }

                    sendSOSMessage(message)
                }
                .addOnFailureListener {
                    sendSOSMessage("SOS! I need help. Location error.")
                }

        } catch (e: SecurityException) {
            Toast.makeText(this, "Location permission error", Toast.LENGTH_SHORT).show()
        }
    }

    // 🔴 SEND SMS
    private fun sendSOSMessage(message: String) {
        SmsUtil.sendSms("9999999999", message)

        Toast.makeText(
            this,
            "SOS Sent Successfully",
            Toast.LENGTH_LONG
        ).show()
    }

    // 🔐 PERMISSIONS
    private fun hasAllPermissions(): Boolean {
        val smsPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        val locationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        return smsPermission == PackageManager.PERMISSION_GRANTED &&
                locationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == permissionCode &&
            grantResults.isNotEmpty() &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        ) {
            triggerSOS()
        }
    }

    // 🔹 SENSOR LIFECYCLE
    override fun onResume() {
        super.onResume()
        val accelerometer =
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorManager.registerListener(
            shakeDetector,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(shakeDetector)
    }

    // 🔹 POWER BUTTON REGISTER
    override fun onStart() {
        super.onStart()
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(powerButtonReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(powerButtonReceiver)
    }
}
