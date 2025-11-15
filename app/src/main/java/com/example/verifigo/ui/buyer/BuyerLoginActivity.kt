package com.example.verifigo.ui.buyer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.verifigo.databinding.ActivityBuyerLoginBinding
import com.example.verifigo.viewmodel.AuthenticationViewModel

class BuyerLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyerLoginBinding
    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuyerLogin.setOnClickListener {
            handleLogin()
        }

        // Link the Register text view to the registration activity
        binding.tvRegisterBuyer.setOnClickListener {
            val intent = Intent(this, BuyerRegistrationActivity::class.java)
            startActivity(intent)
        }

        // Observe the login result from the ViewModel
        authViewModel.buyerLoginResult.observe(this) { buyer ->
            if (buyer != null) {
                // Login successful!
                Toast.makeText(this, "Welcome back, ${buyer.name}!", Toast.LENGTH_LONG).show()
                // Redirect to the Vehicle List
                val intent = Intent(this, VehicleListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else if (buyer == null && !binding.etBuyerEmail.text.isNullOrEmpty()) {
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

        // Trigger the login via the ViewModel
        authViewModel.loginBuyer(email, password)
    }
}