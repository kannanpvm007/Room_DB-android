package com.blogspot.roomdb.dbUtils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserEntity::class], version = 2, exportSchema = false,  )
@TypeConverters(com.blogspot.roomdb.dbUtils.TypeConverters::class)
abstract class UserDb : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSATNCE: UserDb? = null
        fun getUserDB(context: Context): UserDb {
            val tempInstance = INSATNCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDb::class.java, "user_db")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/user_db.db")
                    .build()
                return instance
            }
        }
    }
}