package com.mod_int.carwash.data.repo

import com.mod_int.carwash.data.source.local.UserLocalDataSource
import com.mod_int.carwash.room.entity.UserEntity
import com.mod_int.carwash.util.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userLocalDataSource: UserLocalDataSource) :
    UserRepository {

    override suspend fun registerUser(userEntity: UserEntity): Boolean =
        userLocalDataSource.registerUser(userEntity)

    override suspend fun getUserInfo(email: String): Result<UserEntity> =
        userLocalDataSource.getUserInfo(email)

    override suspend fun delete(userEntity: UserEntity) {
        userLocalDataSource.delete(userEntity)
    }

}