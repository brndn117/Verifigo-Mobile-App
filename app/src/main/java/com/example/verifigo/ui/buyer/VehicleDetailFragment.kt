package com.example.verifigo.ui.buyer

import android.content.Intent
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

class VehicleDetailFragment : Fragment() {

    private var _binding: FragmentVehicleDetailBinding? = null
    private val binding get() = _binding!!
    private var vehicle: Vehicle? = null

    // TEMPORARY: Placeholder for checking if a Buyer is logged in.
    // In a final app, this flag would be managed by a Session Manager or Shared Preferences.
    private var isBuyerLoggedIn = false

    companion object {
        private const val ARG_VEHICLE = "vehicle"
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
            vehicle = it.getParcelable(ARG_VEHICLE)
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

        // Logic for the Authenticate button
        binding.btnAuthenticate.setOnClickListener {
            // FIX: Require login before proceeding to authentication screen
            if (isBuyerLoggedIn) {
                // User is logged in, proceed to the authentication screen
                val intent = Intent(context, AuthenticateVehicleActivity::class.java)
                intent.putExtra("VEHICLE_ID", vehicle!!.id)
                startActivity(intent)
            } else {
                // User is not logged in, prompt them and redirect to login
                Toast.makeText(context, "Please log in to run an authentication report.", Toast.LENGTH_LONG).show()
                // Redirect to the Buyer Login screen
                val intent = Intent(context, BuyerLoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun displayVehicleDetails(vehicle: Vehicle) {
        binding.tvVehicleName.text = "${vehicle.make} ${vehicle.model} (${vehicle.year})"
        binding.tvMileage.text = "Mileage: ${vehicle.mileage} km"
        binding.tvPrice.text = "Price: KSH ${"%,.0f".format(vehicle.price)}"

        Glide.with(this)
            .load(vehicle.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivVehicleImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}