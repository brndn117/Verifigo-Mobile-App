package com.example.verifigo.ui.buyer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.verifigo.R
import com.example.verifigo.model.Vehicle

class VehicleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_detail) // We will create this layout next

        val vehicle = intent.getParcelableExtra<Vehicle>("vehicle")

        if (savedInstanceState == null && vehicle != null) {
            val fragment = VehicleDetailFragment.newInstance(vehicle)
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_container, fragment)
                .commit()
        }
    }
}