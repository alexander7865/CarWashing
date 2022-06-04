package com.mod_int.carwash.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mod_int.carwash.room.dao.UserDao
import com.mod_int.carwash.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}