package com.mod_int.carwash.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.google.android.gms.tasks.Task
import com.mod_int.carwash.model.RoomTest
import dagger.hilt.android.AndroidEntryPoint

class TestRoomRepository (
    private val testRoomDao: TestRoomDao
    ) {
    val allTestRoom : LiveData<List<RoomTest>> = testRoomDao.getAll()

    suspend fun insert(room: RoomTest){
        testRoomDao.insert(room)
    }

    suspend fun delete(room: RoomTest){
        testRoomDao.delete(room)
    }

    suspend fun deleteAll() {
        testRoomDao.deleteAll()
    }

    suspend fun update(room: RoomTest) {
        testRoomDao.update(room)
    }
}