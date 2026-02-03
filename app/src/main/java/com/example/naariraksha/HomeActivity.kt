package com.example.naariraksha

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Location
import android.media.AudioManager
import android.media.ToneGenerator
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.naariraksha.data.AppDatabase
import com.example.naariraksha.util.ShakeDetector
import com.example.naariraksha.util.SmsUtil
import com.example.naariraksha.util.PowerButtonReceiver
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.example.naariraksha.ui.FakeCallActivity
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sensorManager: SensorManager
    private lateinit var shakeDetector: ShakeDetector
    private lateinit var powerButtonReceiver: PowerButtonReceiver
    private lateinit var database: AppDatabase
    private var isSirenPlaying = false
    private var toneGenerator: ToneGenerator? = null
    private var safetyTimer: CountDownTimer? = null

    private val permissionCode = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        database = AppDatabase.getDatabase(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val sosButton = findViewById<RelativeLayout>(R.id.btnSOS)
        val cardSiren = findViewById<CardView>(R.id.cardSiren)
        val cardTimer = findViewById<CardView>(R.id.cardTimer)
        val cardHelplines = findViewById<CardView>(R.id.cardHelplines)
        val cardContacts = findViewById<CardView>(R.id.cardContacts)

        sosButton.setOnClickListener {
            if (hasAllPermissions()) {
                triggerSOS()
            } else {
                requestPermissions()
            }
        }

        cardTimer.setOnClickListener {
            showSafetyTimerDialog()
        }

        cardSiren.setOnClickListener {
            toggleSiren()
        }

        cardHelplines.setOnClickListener {
            startActivity(Intent(this, HelplinesActivity::class.java))
        }

        cardContacts.setOnClickListener {
            startActivity(Intent(this, ContactsActivity::class.java))
        }

        // 🔹 SHAKE DETECTOR
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        shakeDetector = ShakeDetector {
            if (hasAllPermissions()) {
                triggerSOS()
                Toast.makeText(this, "Shake detected! SOS triggered", Toast.LENGTH_SHORT).show()
            }
        }

        // 🔹 POWER BUTTON RECEIVER
        powerButtonReceiver = PowerButtonReceiver {
            if (hasAllPermissions()) {
                triggerSOS()
                Toast.makeText(this, "Power button SOS triggered", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSafetyTimerDialog() {
        val options = arrayOf("10 Minutes", "20 Minutes", "30 Minutes", "Cancel Active Timer")
        AlertDialog.Builder(this, R.style.Theme_Material3_DayNight_Dialog_Alert)
            .setTitle("Safe Walk Timer")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> startSafetyTimer(10)
                    1 -> startSafetyTimer(20)
                    2 -> startSafetyTimer(30)
                    3 -> cancelSafetyTimer()
                }
            }
            .show()
    }

    private fun startSafetyTimer(minutes: Int) {
        safetyTimer?.cancel()
        val millis = minutes * 60 * 1000L
        
        Toast.makeText(this, "Safety Timer started for $minutes mins", Toast.LENGTH_LONG).show()
        
        safetyTimer = object : CountDownTimer(millis, 60000) {
            override fun onTick(millisUntilFinished: Long) {
                // Could update UI if needed
            }

            override fun onFinish() {
                triggerSOS()
                Toast.makeText(this@HomeActivity, "Timer expired! SOS Triggered", Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    private fun cancelSafetyTimer() {
        safetyTimer?.cancel()
        safetyTimer = null
        Toast.makeText(this, "Safety Timer Cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun toggleSiren() {
        if (!isSirenPlaying) {
            isSirenPlaying = true
            toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 10000)
            Toast.makeText(this, "Siren Started!", Toast.LENGTH_SHORT).show()
        } else {
            isSirenPlaying = false
            toneGenerator?.stopTone()
            toneGenerator?.release()
            toneGenerator = null
            Toast.makeText(this, "Siren Stopped", Toast.LENGTH_SHORT).show()
        }
    }

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
                    sendSOSToAllContacts(message)
                }
                .addOnFailureListener {
                    sendSOSToAllContacts("SOS! I need help. Location error.")
                }

        } catch (e: SecurityException) {
            Toast.makeText(this, "Location permission error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendSOSToAllContacts(message: String) {
        lifecycleScope.launch {
            val contacts = database.contactDao().getContactsOnce()
            if (contacts.isEmpty()) {
                SmsUtil.sendSms("9999999999", message)
            } else {
                for (contact in contacts) {
                    SmsUtil.sendSms(contact.phoneNumber, message)
                }
            }
        }
        Toast.makeText(this, "SOS Sent Successfully", Toast.LENGTH_LONG).show()
    }

    private fun hasAllPermissions(): Boolean {
        val smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        val locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return smsPermission == PackageManager.PERMISSION_GRANTED &&
                locationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION),
            permissionCode
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionCode && grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            triggerSOS()
        }
    }

    override fun onResume() {
        super.onResume()
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(shakeDetector)
    }

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
        toneGenerator?.release()
    }
}
