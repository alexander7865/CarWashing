package com.mod_int.carwash.data.source.local

import com.mod_int.carwash.room.database.UserDatabase
import com.mod_int.carwash.room.entity.UserEntity
import com.mod_int.carwash.util.Result
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(private val userDatabase: UserDatabase) :
    UserLocalDataSource {

    override suspend fun registerUser(userEntity: UserEntity): Boolean {
        val result = userDatabase.userDao().register(userEntity)
        return result > 0
    }

    override suspend fun getUserInfo(email: String): Result<UserEntity> {
        return try {
            val getUserInfo = userDatabase.userDao().findByEmail(email)
            Result.Success(getUserInfo)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}