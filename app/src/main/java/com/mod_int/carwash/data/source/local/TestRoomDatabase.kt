package com.mod_int.carwash.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mod_int.carwash.model.RoomTest
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [RoomTest::class], version = 1, exportSchema = false)
abstract class TestRoomDatabase : RoomDatabase() {
    abstract fun testRoomDao() : TestRoomDao

    companion object {
        @Volatile
        private var INSTANCE: TestRoomDatabase? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): TestRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestRoomDatabase::class.java,
                    "room_test"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}