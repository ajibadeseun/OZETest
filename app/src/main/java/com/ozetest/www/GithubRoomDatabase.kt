package com.ozetest.www

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Executors

@Database(entities = [Github::class], version = 1, exportSchema = false)
abstract class GithubRoomDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    // marking the instance as volatile to ensure atomic access to the variable
    companion object{
        @Volatile
        private var INSTANCE: GithubRoomDatabase? = null

        fun getDatabase(context: Context): GithubRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(GithubRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GithubRoomDatabase::class.java,
                            "users_database")
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}