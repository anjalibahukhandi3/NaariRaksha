package com.example.naariraksha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.naariraksha.data.api.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import com.example.naariraksha.data.api.LoginRequest
import com.example.naariraksha.data.api.UserRegisterRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val tvSignup = findViewById<TextView>(R.id.tvSignup)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                performLogin(email, password)
            } else {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }

        tvSignup.setOnClickListener {
            // Simplified registration for demo - just use fixed credentials if backend not running
            // or we could create a RegisterActivity. For now, let's keep it simple.
            Toast.makeText(this, "Registration restricted in demo mode", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performLogin(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val request = LoginRequest().apply {
                    this.email = email
                    this.password = password
                }
                
                val response = RetrofitClient.instance.login(request)
                
                if (response.isSuccessful) {
                    val loginBody = response.body()
                    if (loginBody?.status == "success") {
                        Toast.makeText(this@LoginActivity, "Welcome back, ${loginBody.name}!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, loginBody?.message ?: "Login Failed", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // If server is not reachable, fallback to offline demo mode for development
                    Toast.makeText(this@LoginActivity, "Offline Mode: Backend unreachable", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                }
            } catch (e: Exception) {
                // Network error - fallback to demo mode
                Toast.makeText(this@LoginActivity, "Demo Mode Active", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            }
        }
    }
}
