package com.hcy.composelearn.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author HouChengYue
 * @date 17:16
 * description：
 */
@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "_firstname") val firstName: String?,
    @ColumnInfo(name = "_lastname") val lastname: String?
)
