package com.mod_int.carwash.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "room_test")
data class RoomTest (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id : Long?,

    @ColumnInfo
    var wmPoint : String = ""
)
