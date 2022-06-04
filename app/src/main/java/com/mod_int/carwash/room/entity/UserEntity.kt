package com.mod_int.carwash.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_email") var userEmail: String,
    @ColumnInfo(name = "car_num") val carNum: String,
    @ColumnInfo(name = "car_brand") val carBrand: String,
    @ColumnInfo(name = "car_model_name") val carModelName: String,
    @ColumnInfo(name = "car_category") val carCategory: String,
    @ColumnInfo(name = "car_size") val carSize: String,
    @ColumnInfo(name = "car_origin") val carOrigin: String,
    @ColumnInfo(name = "car_color") val carColor: String,
    @ColumnInfo(name = "car_location") val carLocation: String,
)