package com.mod_int.carwash.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mod_int.carwash.model.RoomTest

@Dao
interface TestRoomDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insert(room : RoomTest)

    @Query("SELECT * FROM room_test ORDER BY id DESC")
    fun getAll() : LiveData<List<RoomTest>>

    @Delete
    suspend fun delete(room: RoomTest)

}