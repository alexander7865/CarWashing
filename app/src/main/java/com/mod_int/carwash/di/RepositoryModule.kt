package com.mod_int.carwash.di


import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.data.repo.FirebaseRepositoryImpl
import com.mod_int.carwash.data.repo.UserRepository
import com.mod_int.carwash.data.repo.UserRepositoryImpl
import com.mod_int.carwash.data.source.local.UserLocalDataSource
import com.mod_int.carwash.data.source.local.UserLocalDataSourceImpl
import com.mod_int.carwash.data.source.remote.FirebaseRemoteDataSource
import com.mod_int.carwash.data.source.remote.FirebaseRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

    @Singleton
    @Binds
    abstract fun bindFirebaseRemoteDataSource(firebaseRemoteDataSourceImpl: FirebaseRemoteDataSourceImpl): FirebaseRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

}
