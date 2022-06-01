package com.mod_int.carwash.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mod_int.carwash.model.RoomTest

//룸데이터베이스를 레포지토리 패턴으로 적용하고 싶은데 어떻게 해야하나요?
@Dao
interface TestRoomDao {
    @Query("SELECT * FROM room_test ORDER BY id ASC")
    fun getAll() : LiveData<List<RoomTest>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(room : RoomTest)

    @Delete
    suspend fun delete(room: RoomTest)

    @Query(value = "DELETE FROM room_test")
    suspend fun deleteAll()

    @Update
    suspend fun update(room: RoomTest)

}
