package com.mod_int.carwash.room.dao

import androidx.room.*
import com.mod_int.carwash.room.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM USER_TABLE WHERE `user_email` = (:email)")
    fun findByEmail(email: String): UserEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun register(userEntity: UserEntity): Long

    @Delete
    fun delete(userEntity: UserEntity)
}