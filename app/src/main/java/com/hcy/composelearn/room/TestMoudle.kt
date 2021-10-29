package com.hcy.composelearn.room

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room

/**
 * @author HouChengYue
 * @date 17:27
 * description：
 */
class TestMoudle(val context:Context) : ViewModel() {

    val db = Room.databaseBuilder(context,AppDatabase::class.java,"user_datbase")
        .build()




}
