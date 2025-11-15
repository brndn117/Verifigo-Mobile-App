package com.example.verifigo.dao

import androidx.room.*
import com.example.verifigo.model.Buyer
import kotlinx.coroutines.flow.Flow

@Dao
interface BuyerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerBuyer(buyer: Buyer)

    @Query("SELECT * FROM buyers WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Buyer?
}