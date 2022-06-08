package com.mod_int.carwash.data.repo

import com.mod_int.carwash.room.entity.UserEntity
import com.mod_int.carwash.util.Result

interface UserRepository {

    suspend fun registerUser(userEntity: UserEntity): Boolean

    suspend fun getUserInfo(email: String): Result<UserEntity>

    suspend fun delete(userEntity: UserEntity)

}