package com.example.verifigo.ui.seller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.verifigo.databinding.ActivitySellerRegistrationBinding
import com.example.verifigo.model.Seller
import com.example.verifigo.viewmodel.AuthenticationViewModel

class SellerRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellerRegistrationBinding
    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterSeller.setOnClickListener {
            handleRegistration()
        }

        // Link to go back to the login screen
        binding.tvBackToLogin.setOnClickListener {
            finish() // Closes this activity and returns to SellerLoginActivity
        }
    }

    private fun handleRegistration() {
        val name = binding.etSellerName.text.toString().trim()
        val email = binding.etSellerRegEmail.text.toString().trim()
        val password = binding.etSellerRegPassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields to register.", Toast.LENGTH_SHORT).show()
            return
        }

        // 1. Create the new Seller object
        val newSeller = Seller(name = name, email = email, password = password)

        // 2. Register the Seller via the ViewModel
        authViewModel.registerSeller(newSeller)

        Toast.makeText(this, "Registration successful! Logging you in...", Toast.LENGTH_SHORT).show()

        // 3. Immediately attempt login and redirect to Dashboard
        authViewModel.loginSeller(email, password)

        // We rely on the observer in SellerLoginActivity to redirect, but we can force it here for simplicity
        val intent = Intent(this, SellerDashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}