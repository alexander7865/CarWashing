package com.mod_int.carwash.data.source.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.RoomTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestRoomViewModel (
    app: Application,
    application: Application) : BaseViewModel(app) {
    private val repo : TestRoomRepository
    private val allTest : LiveData<List<RoomTest>>

    init{
        val dao = TestRoomDatabase.getDatabase(application).testRoomDao()
        repo = TestRoomRepository(dao)
        allTest = repo.allTestRoom
    }

    fun insert(room: RoomTest) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(room)
    }

    fun delete(room: RoomTest) = viewModelScope.launch(Dispatchers.IO){
        repo.delete(room)
    }
}