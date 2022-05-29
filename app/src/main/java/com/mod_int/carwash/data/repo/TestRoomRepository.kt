package com.mod_int.carwash.data.repo

import androidx.lifecycle.LiveData
import com.mod_int.carwash.data.source.local.TestRoomDAO
import com.mod_int.carwash.model.RoomTest

class TestRoomRepository(private val testRoomDAO: TestRoomDAO) {
    val allTestRoom : LiveData<List<RoomTest>> = testRoomDAO.getAll()

    suspend fun insert(roomTest: RoomTest){
        testRoomDAO.insert(roomTest)
    }

    suspend fun delete(roomTest: RoomTest){
        testRoomDAO.delete(roomTest)
    }
}