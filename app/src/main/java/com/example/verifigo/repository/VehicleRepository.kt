package com.example.verifigo.repository

import com.example.verifigo.dao.VehicleDao
import com.example.verifigo.model.Vehicle
import kotlinx.coroutines.flow.Flow

class VehicleRepository(private val dao: VehicleDao) {
    val allVehicles: Flow<List<Vehicle>> = dao.getAllVehicles()
    suspend fun insert(vehicle: Vehicle) = dao.insertVehicle(vehicle)
}