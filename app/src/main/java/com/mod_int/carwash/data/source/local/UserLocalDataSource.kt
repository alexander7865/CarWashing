package com.mod_int.carwash.data.source.local

import com.mod_int.carwash.room.entity.UserEntity
import com.mod_int.carwash.util.Result

interface UserLocalDataSource {

    suspend fun registerUser(userEntity: UserEntity): Boolean

    suspend fun getUserInfo(email: String): Result<UserEntity>
}