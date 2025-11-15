package com.example.verifigo.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.verifigo.databinding.ActivityHomeBinding
import com.example.verifigo.ui.buyer.VehicleListActivity
import com.example.verifigo.ui.seller.SellerLoginActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use ViewBinding to inflate the layout
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Buyer button routes to the vehicle list screen
        binding.btnBuyerRole.setOnClickListener {
            val intent = Intent(this, VehicleListActivity::class.java)
            startActivity(intent)
        }

        // Seller button routes to the seller login screen
        binding.btnSellerRole.setOnClickListener {
            val intent = Intent(this, SellerLoginActivity::class.java)
            startActivity(intent)
        }
    }
}