package com.example.verifigo.dao

import com.example.verifigo.model.AuthenticationReport
import androidx.room.*

@Dao
interface AuthenticationReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: AuthenticationReport)

    @Query("SELECT * FROM authentication_reports WHERE vehicleId = :vehicleId ORDER BY reportDate DESC LIMIT 1")
    suspend fun getLatestReportByVehicleId(vehicleId: Int): AuthenticationReport?
}
