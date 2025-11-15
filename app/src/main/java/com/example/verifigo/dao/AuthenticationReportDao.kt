package com.example.verifigo.dao

import androidx.room.*
import com.example.verifigo.model.AuthenticationReport

@Dao
interface AuthenticationReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: AuthenticationReport)

    @Query("SELECT * FROM authentication_reports WHERE vehicleId = :vehicleId ORDER BY reportDate DESC LIMIT 1")
    fun getLatestReportByVehicleId(vehicleId: Int): AuthenticationReport?
}