package com.example.verifigo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val make: String,
    val model: String,
    val year: Int,
    val price: Double,
    val mileage: Int,
    val imageUrl: String
) : Parcelable