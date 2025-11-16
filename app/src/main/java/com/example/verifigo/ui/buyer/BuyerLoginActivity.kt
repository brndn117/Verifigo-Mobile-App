package com.example.verifigo.ui.buyer

import android.content.Context // Added for SharedPreferences
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.verifigo.databinding.ActivityBuyerLoginBinding
import com.example.verifigo.ui.authentication.AuthenticateVehicleActivity
import com.example.verifigo.viewmodel.AuthenticationViewModel

class BuyerLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyerLoginBinding
    private val authViewModel: AuthenticationViewModel by viewModels()

    // --- NEW: To store the vehicle ID passed from the previous screen ---
    private var postLoginVehicleId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- NEW: Retrieve the vehicle ID from the intent ---
        postLoginVehicleId = intent.getIntExtra(VehicleDetailFragment.POST_LOGIN_VEHICLE_ID, -1)

        binding.btnBuyerLogin.setOnClickListener {
            handleLogin()
        }

        binding.tvRegisterBuyer.setOnClickListener {
            val intent = Intent(this, BuyerRegistrationActivity::class.java)
            startActivity(intent)
        }

        // --- UPDATED: The observer now handles conditional navigation ---
        authViewModel.buyerLoginResult.observe(this) { buyer ->
            if (buyer != null) {
                // Login successful!
                Toast.makeText(this, "Welcome back, ${buyer.name}!", Toast.LENGTH_SHORT).show()

                // --- NEW: Save login state for future checks ---
                saveLoginState()

                // --- UPDATED NAVIGATION LOGIC ---
                if (postLoginVehicleId != -1) {
                    // If a vehicle ID was passed, redirect to the authentication screen
                    val intent = Intent(this, AuthenticateVehicleActivity::class.java).apply {
                        putExtra("VEHICLE_ID", postLoginVehicleId)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                } else {
                    // Otherwise, redirect to the default Vehicle List screen
                    val intent = Intent(this, VehicleListActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                }
                finish() // Finish LoginActivity so user can't go back to it

            } else if (!binding.etBuyerEmail.text.isNullOrEmpty()) {
                // Login failed
                Toast.makeText(this, "Login failed. Check email and password.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleLogin() {
        val email = binding.etBuyerEmail.text.toString().trim()
        val password = binding.etBuyerPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
            return
        }

        authViewModel.loginBuyer(email, password)
    }

    // --- NEW: Function to persist the login state ---
    private fun saveLoginState() {
        val prefs = getSharedPreferences("VerifigoPrefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("IS_LOGGED_IN", true).apply()
    }
}
