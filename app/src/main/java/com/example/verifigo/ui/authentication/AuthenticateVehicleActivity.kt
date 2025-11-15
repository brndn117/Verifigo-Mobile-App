package com.example.verifigo.ui.authentication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.verifigo.databinding.ActivityAuthenticateVehicleBinding
import com.example.verifigo.viewmodel.AuthenticationViewModel
import java.text.SimpleDateFormat
import java.util.*

class AuthenticateVehicleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticateVehicleBinding

    // FIX: Ensure the ViewModel is initialized using the correct delegate
    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticateVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardResult.visibility = View.GONE

        val vehicleIdFromIntent = intent.getIntExtra("VEHICLE_ID", 0)
        if (vehicleIdFromIntent != 0) {
            binding.etVehicleId.setText(vehicleIdFromIntent.toString())
        }

        binding.btnAuthenticate.setOnClickListener {
            runAuthentication()
        }

        // FIX: Observation is now correct, relying on the fixed ViewModel property
        authViewModel.authenticationResult.observe(this) { report ->
            report?.let {
                binding.cardResult.visibility = View.VISIBLE

                binding.tvReportStatus.text = "Status: ${it.status.replace("_", " ")}"

                val date = Date(it.reportDate)
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                binding.tvReportDate.text = "Report Date: ${formatter.format(date)}"

                if (it.status.contains("CLEAN")) {
                    binding.cardResult.setCardBackgroundColor(resources.getColor(android.R.color.holo_green_light, theme))
                } else {
                    binding.cardResult.setCardBackgroundColor(resources.getColor(android.R.color.holo_red_light, theme))
                }
            }
        }
    }

    private fun runAuthentication() {
        val idString = binding.etVehicleId.text.toString().trim()

        if (idString.isEmpty()) {
            Toast.makeText(this, "Please enter a Vehicle ID.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val vehicleId = idString.toInt()
            authViewModel.authenticateVehicle(vehicleId)

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid number for Vehicle ID.", Toast.LENGTH_SHORT).show()
        }
    }
}