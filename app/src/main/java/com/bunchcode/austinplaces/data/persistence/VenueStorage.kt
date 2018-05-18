package com.bunchcode.austinplaces.data.persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.bunchcode.austinplaces.data.Venue

@Dao
interface VenueStorage {

    @Query("SELECT id FROM venue WHERE isFavorite = 1")
    fun getFavorited(): LiveData<List<String>>

    @Query("SELECT isFavorite FROM venue WHERE id = :venueId")
    fun isFavorited(venueId: String): LiveData<Boolean>

    @Query("SELECT isFavorite FROM venue WHERE id = :venueId")
    fun isFavoritedSync(venueId: String): Boolean

    @Insert(onConflict = REPLACE)
    fun saveVenue(venue: Venue)

    @Delete
    fun deleteVenue(venue: Venue)
}