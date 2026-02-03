package com.example.naariraksha

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HelplinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helplines)

        findViewById<CardView>(R.id.cardWomenHelpline).setOnClickListener {
            dialNumber("1091")
        }

        findViewById<CardView>(R.id.cardPolice).setOnClickListener {
            dialNumber("100")
        }

        findViewById<CardView>(R.id.cardCyber).setOnClickListener {
            dialNumber("1930")
        }
    }

    private fun dialNumber(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }
}
