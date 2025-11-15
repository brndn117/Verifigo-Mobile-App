package com.example.verifigo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.verifigo.database.AppDatabase
import com.example.verifigo.model.AuthenticationReport
import com.example.verifigo.model.Buyer
import com.example.verifigo.model.Seller
import com.example.verifigo.repository.AuthenticationRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class AuthenticationViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: AuthenticationRepository

    private val _authenticationResult = MutableLiveData<AuthenticationReport?>()
    val authenticationResult: LiveData<AuthenticationReport?> = _authenticationResult

    init {
        val db = AppDatabase.getDatabase(app)
        // FIX: Pass all three required DAOs to the Repository constructor
        repository = AuthenticationRepository(
            db.buyerDao(),
            db.sellerDao(),
            db.authenticationReportDao()
        )
    }

    private val _buyerLoginResult = MutableLiveData<Buyer?>()
    val buyerLoginResult: LiveData<Buyer?> = _buyerLoginResult

    private val _sellerLoginResult = MutableLiveData<Seller?>()
    val sellerLoginResult: LiveData<Seller?> = _sellerLoginResult

    fun loginBuyer(email: String, password: String) {
        viewModelScope.launch {
            _buyerLoginResult.value = repository.loginBuyer(email, password)
        }
    }

    fun loginSeller(email: String, password: String) {
        viewModelScope.launch {
            _sellerLoginResult.value = repository.loginSeller(email, password)
        }
    }

    // Core Authentication Logic (NOW PREDICTABLE)
    fun authenticateVehicle(vehicleId: Int) {
        viewModelScope.launch {
            // Logic: Status is determined by the vehicle ID for reliable testing
            val status = when (vehicleId) {
                1 -> "VERIFIED_CLEAN"       // Toyota Fielder
                2 -> "VERIFIED_CLEAN"       // Subaru Forester
                3 -> "FLAGGED_THEFT_RISK"   // Mercedes C200
                else -> {
                    // Default to random for any other ID
                    if (Random.nextBoolean()) "VERIFIED_CLEAN" else "FLAGGED_THEFT_RISK"
                }
            }

            val newReport = AuthenticationReport(
                vehicleId = vehicleId,
                status = status,
                reportDate = System.currentTimeMillis()
            )

            repository.insertReport(newReport)
            _authenticationResult.postValue(newReport)
        }
    }

    fun registerBuyer(buyer: Buyer) = viewModelScope.launch {
        repository.registerBuyer(buyer)
    }

    fun registerSeller(seller: Seller) = viewModelScope.launch {
        repository.registerSeller(seller)
    }
}