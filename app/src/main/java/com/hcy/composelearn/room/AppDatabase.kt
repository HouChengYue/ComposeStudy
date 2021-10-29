package com.hcy.composelearn.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author HouChengYue
 * @date 17:25
 * description：
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usrDao():UserDao

}
