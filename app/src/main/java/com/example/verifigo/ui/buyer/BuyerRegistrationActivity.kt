package com.example.verifigo.ui.buyer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.verifigo.databinding.ActivityBuyerRegistrationBinding
import com.example.verifigo.model.Buyer
import com.example.verifigo.viewmodel.AuthenticationViewModel

class BuyerRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyerRegistrationBinding
    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterBuyer.setOnClickListener {
            handleRegistration()
        }

        // Link to go back to the login screen
        binding.tvBackToLogin.setOnClickListener {
            finish() // Closes this activity and returns to BuyerLoginActivity
        }
    }

    private fun handleRegistration() {
        val name = binding.etBuyerName.text.toString().trim()
        val email = binding.etBuyerRegEmail.text.toString().trim()
        val password = binding.etBuyerRegPassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields to register.", Toast.LENGTH_SHORT).show()
            return
        }

        val newBuyer = Buyer(name = name, email = email, password = password)
        authViewModel.registerBuyer(newBuyer)

        Toast.makeText(this, "Registration successful! Loading vehicles...", Toast.LENGTH_SHORT).show()

        // After registration, immediately redirect to the Vehicle List
        val intent = Intent(this, VehicleListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}