package com.example.verifigo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sellers")
data class Seller(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String
)