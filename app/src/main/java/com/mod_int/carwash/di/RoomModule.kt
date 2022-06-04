package com.mod_int.carwash.di

import android.content.Context
import androidx.room.Room
import com.mod_int.carwash.room.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {



    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext appContext: Context): UserDatabase =
        Room.databaseBuilder(
            appContext,
            UserDatabase::class.java, "user_table"
        ).fallbackToDestructiveMigration().build()

}