package com.example.verifigo.ui.seller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.verifigo.databinding.ActivitySellerLoginBinding
import com.example.verifigo.viewmodel.AuthenticationViewModel

class SellerLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellerLoginBinding
    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSellerLogin.setOnClickListener {
            handleLogin()
        }

        // FIX: Link the Register text view to the new registration activity
        binding.tvRegisterSeller.setOnClickListener {
            val intent = Intent(this, SellerRegistrationActivity::class.java)
            startActivity(intent)
        }

        // Observe the login result from the ViewModel
        authViewModel.sellerLoginResult.observe(this) { seller ->
            if (seller != null) {
                Toast.makeText(this, "Welcome, ${seller.name}!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, SellerDashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else if (seller == null && !binding.etSellerEmail.text.isNullOrEmpty()) {
                // If the user actually attempted a login (email is not empty) but failed
                Toast.makeText(this, "Login failed. Check email and password.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleLogin() {
        val email = binding.etSellerEmail.text.toString().trim()
        val password = binding.etSellerPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
            return
        }

        authViewModel.loginSeller(email, password)
    }
}