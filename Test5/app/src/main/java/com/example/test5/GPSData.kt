package com.example.test5

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
@Entity(tableName="Locations")
data class GPSData(var timestamp: Long,
                   var latitude: Double,
                   var longitude: Double){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 // integer primary key for the DB
}