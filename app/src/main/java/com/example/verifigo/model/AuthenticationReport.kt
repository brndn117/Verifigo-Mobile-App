package com.example.verifigo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authentication_reports")
data class AuthenticationReport(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleId: Int,
    val reportDate: Long = System.currentTimeMillis(),
    val status: String
)