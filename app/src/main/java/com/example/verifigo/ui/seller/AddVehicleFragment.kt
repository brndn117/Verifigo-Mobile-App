package com.example.verifigo.ui.seller

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.verifigo.R
import com.example.verifigo.databinding.FragmentAddVehicleBinding
import com.example.verifigo.model.Vehicle
import com.example.verifigo.viewmodel.VehicleViewModel

class AddVehicleFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null

    private val vehicleViewModel: VehicleViewModel by viewModels()
    private var _binding: FragmentAddVehicleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVehicleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddVehicle.setOnClickListener {
            saveVehicle()
        }

        // Action for the new Select Image button
        binding.btnSelectImage.setOnClickListener {
            openImageChooser()
        }

        // Initialize placeholder image (important for clean state)
        binding.ivImagePreview.setImageResource(R.drawable.ic_car_placeholder)
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data

            // Display the selected image URI in the ImageView preview using Glide
            Glide.with(this)
                .load(selectedImageUri)
                .placeholder(R.drawable.ic_car_placeholder)
                .into(binding.ivImagePreview)

            Toast.makeText(context, "Image selected successfully.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveVehicle() {
        val make = binding.etMake.text.toString().trim()
        val model = binding.etModel.text.toString().trim()
        val yearString = binding.etYear.text.toString().trim()
        val priceString = binding.etPrice.text.toString().trim()
        val mileageString = binding.etMileage.text.toString().trim()

        // Use the selected image URI string if available, otherwise use a placeholder URL
        val finalImageUrl = selectedImageUri?.toString()
            ?: "https://placehold.co/600x400/CCCCCC/000000?text=Listing+Added"

        if (make.isEmpty() || model.isEmpty() || yearString.isEmpty() || priceString.isEmpty() || mileageString.isEmpty()) {
            Toast.makeText(context, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            return
        }
        // ... (data conversion and saving logic remains the same)
        try {
            val year = yearString.toInt()
            val price = priceString.toDouble()
            val mileage = mileageString.toInt()

            val newVehicle = Vehicle(
                make = make,
                model = model,
                year = year,
                price = price,
                mileage = mileage,
                imageUrl = finalImageUrl
            )

            vehicleViewModel.insert(newVehicle)
            Toast.makeText(context, "Vehicle added successfully!", Toast.LENGTH_SHORT).show()
            clearForm()

        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Please enter valid numbers.", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearForm() {
        binding.etMake.setText("")
        binding.etModel.setText("")
        binding.etYear.setText("")
        binding.etPrice.setText("")
        binding.etMileage.setText("")

        selectedImageUri = null
        // Reset the image preview back to the placeholder icon
        binding.ivImagePreview.setImageResource(R.drawable.ic_car_placeholder)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}