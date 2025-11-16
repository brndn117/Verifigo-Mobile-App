package com.example.verifigo.ui.buyer

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.verifigo.R
import com.example.verifigo.databinding.FragmentVehicleDetailBinding
import com.example.verifigo.model.Vehicle
import com.example.verifigo.ui.authentication.AuthenticateVehicleActivity

// import com.google.firebase.auth.FirebaseAuth // REMOVED

class VehicleDetailFragment : Fragment() {

    private var _binding: FragmentVehicleDetailBinding? = null
    private val binding get() = _binding!!
    private var vehicle: Vehicle? = null

    companion object {
        private const val ARG_VEHICLE = "vehicle"
        const val POST_LOGIN_VEHICLE_ID = "POST_LOGIN_VEHICLE_ID"

        fun newInstance(vehicle: Vehicle) =
            VehicleDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_VEHICLE, vehicle)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            @Suppress("DEPRECATION")
            vehicle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_VEHICLE, Vehicle::class.java)
            } else {
                it.getParcelable(ARG_VEHICLE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vehicle?.let { displayVehicleDetails(it) }

        binding.btnAuthenticate.setOnClickListener {
            handleAuthenticationClick()
        }
    }

    private fun handleAuthenticationClick() {
        val currentVehicle = vehicle ?: run {
            Toast.makeText(requireContext(), "Vehicle data not available.", Toast.LENGTH_SHORT).show()
            return
        }

        // --- NEW LOGIN CHECK (No Firebase) ---
        // We check a value in SharedPreferences to see if the user is logged in.
        val prefs = requireActivity().getSharedPreferences("VerifigoPrefs", Context.MODE_PRIVATE)
        val isUserLoggedIn = prefs.getBoolean("IS_LOGGED_IN", false)
        // You will need to set this "IS_LOGGED_IN" flag to 'true' in your BuyerLoginActivity upon a successful login.

        if (isUserLoggedIn) {
            // If logged in, proceed directly to the authentication screen
            val intent = Intent(requireContext(), AuthenticateVehicleActivity::class.java).apply {
                putExtra("VEHICLE_ID", currentVehicle.id)
            }
            startActivity(intent)
        } else {
            // If not logged in, show a message and redirect to the login screen
            Toast.makeText(requireContext(), "Please log in to authenticate this vehicle.", Toast.LENGTH_LONG).show()

            val intent = Intent(requireContext(), BuyerLoginActivity::class.java).apply {
                putExtra(POST_LOGIN_VEHICLE_ID, currentVehicle.id)
            }
            startActivity(intent)
        }
    }

    private fun displayVehicleDetails(vehicle: Vehicle) {
        binding.tvVehicleName.text = getString(R.string.vehicle_name_format, vehicle.make, vehicle.model, vehicle.year)
        binding.tvMileage.text = getString(R.string.vehicle_mileage_format, vehicle.mileage)
        binding.tvPrice.text = getString(R.string.vehicle_price_format, vehicle.price)

        Glide.with(requireContext())
            .load(vehicle.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_image_not_found)
            .into(binding.ivVehicleImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}