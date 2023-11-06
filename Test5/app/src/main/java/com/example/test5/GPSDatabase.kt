package com.example.test5

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow


@Database(entities= [GPSData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GPSDatabase : RoomDatabase(){
    abstract fun GPSDao(): GPSDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GPSDatabase? = null

        fun getDatabase(context: Context): GPSDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GPSDatabase::class.java,
                    "weather_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


@Dao
interface GPSDAO {

    @Insert
    suspend fun addGPSData(data: GPSData)

    @Query("SELECT * from Locations ORDER BY timestamp DESC LIMIT 1")
    fun latestGPS(): Flow<GPSData>


    @Query("SELECT * from Locations ORDER BY timestamp DESC")
    fun allLocations(): Flow<List<GPSData>>
}



