package com.example.kyoassistance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kyoassistance.database.Entity.ContentEntity
import com.example.kyoassistance.database.Entity.NoteEntity
import com.example.kyoassistance.database.dao.ContentDAO
import com.example.kyoassistance.database.dao.NotesDAO

@Database(entities = [ContentEntity::class ,NoteEntity::class], version = 2)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun contentDAO() : ContentDAO
    abstract fun notesDAO() : NotesDAO

    companion object {
        @Volatile
        private var INSTANCE : ChatDatabase? = null

        fun getDatabase(
            context : Context
        ) : ChatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chatDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
