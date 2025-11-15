package com.example.verifigo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.verifigo.database.AppDatabase
import com.example.verifigo.model.Vehicle
import com.example.verifigo.repository.VehicleRepository
import kotlinx.coroutines.launch

class VehicleViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: VehicleRepository
    val allVehicles = MutableLiveData<List<Vehicle>>()

    init {
        val db = AppDatabase.getDatabase(app)
        repository = VehicleRepository(db.vehicleDao())

        repository.allVehicles.asLiveData().observeForever {
            allVehicles.postValue(it)
        }
    }

    fun insert(vehicle: Vehicle) = viewModelScope.launch {
        repository.insert(vehicle)
    }
}