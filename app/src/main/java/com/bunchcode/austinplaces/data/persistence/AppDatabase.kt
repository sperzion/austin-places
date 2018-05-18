package com.bunchcode.austinplaces.data.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.bunchcode.austinplaces.data.Venue

@Database(entities = [(Venue::class)], version = 1)
abstract class AppDatabase: RoomDatabase() {

    companion object {

    private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if (null == instance) {
                synchronized (AppDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java,
                            "austin_places").build()
                }
            }
            return instance!!
        }
    }

    abstract fun getVenueStorage(): VenueStorage
}