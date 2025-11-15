package com.example.verifigo.ui.seller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.verifigo.R
import com.example.verifigo.ui.seller.AddVehicleFragment // <--- THE MISSING IMPORT

class SellerDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_dashboard)

        // Check if the fragment container is empty (first launch)
        if (savedInstanceState == null) {
            // Load the AddVehicleFragment immediately after login
            val fragment = AddVehicleFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.seller_fragment_container, fragment)
                .commit()
        }
    }
}