package com.example.verifigo.dao

import androidx.room.*
import com.example.verifigo.model.Seller

@Dao
interface SellerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerSeller(seller: Seller)

    @Query("SELECT * FROM sellers WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Seller?
}