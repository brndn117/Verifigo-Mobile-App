package com.example.verifigo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase // <-- MISSING IMPORT 1
import androidx.sqlite.db.SupportSQLiteDatabase // <-- MISSING IMPORT 2
import com.example.verifigo.dao.AuthenticationReportDao
import com.example.verifigo.dao.BuyerDao
import com.example.verifigo.dao.SellerDao
import com.example.verifigo.dao.VehicleDao
import com.example.verifigo.model.AuthenticationReport
import com.example.verifigo.model.Buyer
import com.example.verifigo.model.Seller
import com.example.verifigo.model.Vehicle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Buyer::class, Seller::class, Vehicle::class, AuthenticationReport::class],
    version = 1,
    exportSchema = false
)
// FIX 1: Must extend RoomDatabase, not itself
abstract class AppDatabase : RoomDatabase() {
    abstract fun buyerDao(): BuyerDao
    abstract fun sellerDao(): SellerDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun authenticationReportDao(): AuthenticationReportDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "verifigo_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(context.applicationContext))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // FIX 2: Must extend RoomDatabase.Callback
    private class AppDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                prePopulateDatabase(getDatabase(context).vehicleDao())
            }
        }

        suspend fun prePopulateDatabase(vehicleDao: VehicleDao) {
            val vehicles = listOf(
                Vehicle(
                    make = "Toyota",
                    model = "Fielder",
                    year = 2018,
                    price = 2800000.00,
                    mileage = 45000,
                    imageUrl = "https://placehold.co/600x400/0000FF/FFFFFF?text=Blue+Fielder"
                ),
                Vehicle(
                    make = "Subaru",
                    model = "Forester",
                    year = 2021,
                    price = 4500000.00,
                    mileage = 15000,
                    imageUrl = "https://placehold.co/600x400/008000/FFFFFF?text=Green+Forester"
                ),
                Vehicle(
                    make = "Mercedes-Benz",
                    model = "C200",
                    year = 2017,
                    price = 5800000.00,
                    mileage = 62000,
                    imageUrl = "https://placehold.co/600x400/808080/FFFFFF?text=Grey+C200"
                )
            )
            vehicles.forEach { vehicleDao.insertVehicle(it) }
        }
    }
}