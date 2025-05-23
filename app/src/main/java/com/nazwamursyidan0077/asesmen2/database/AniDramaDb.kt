package com.nazwamursyidan0077.asesmen2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nazwamursyidan0077.asesmen2.model.AniDrama

@Database(entities = [AniDrama::class], version = 2, exportSchema = false) // versi ditingkatkan
abstract class AniDramaDb : RoomDatabase() {
    abstract val dao: AniDramaDao

    companion object {
        @Volatile
        private var INSTANCE: AniDramaDb? = null

        fun getInstance(context: Context): AniDramaDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AniDramaDb::class.java,
                        "anidrama.db"
                    )
                        .fallbackToDestructiveMigration() // akan reset database saat skema berubah
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
