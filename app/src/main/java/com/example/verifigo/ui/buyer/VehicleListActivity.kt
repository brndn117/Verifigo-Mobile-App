package com.example.verifigo.ui.buyer

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.verifigo.databinding.ActivityVehicleListBinding
import com.example.verifigo.viewmodel.VehicleViewModel

class VehicleListActivity : AppCompatActivity() {

    private val viewModel: VehicleViewModel by viewModels()
    private lateinit var binding: ActivityVehicleListBinding
    private lateinit var adapter: VehicleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeVehicles()
    }

    private fun setupRecyclerView() {
        adapter = VehicleAdapter(emptyList()) { vehicle ->
            // Handle item click: Navigate to detail view
            val intent = Intent(this, VehicleDetailActivity::class.java)
            intent.putExtra("vehicle", vehicle)
            startActivity(intent)
        }

        binding.recyclerViewVehicles.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewVehicles.adapter = adapter
    }

    private fun observeVehicles() {
        // Observe the list of vehicles from the database via ViewModel
        viewModel.allVehicles.observe(this) { vehicles ->
            vehicles?.let { adapter.updateList(it) }
        }
    }
}