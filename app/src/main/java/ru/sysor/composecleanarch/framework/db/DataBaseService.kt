package ru.sysor.composecleanarch.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class DataBaseService: RoomDatabase(){

    companion object{
        private const val DATABASE_NAME = "note.db"
        private var instance: DataBaseService? = null

        private fun create(context: Context):DataBaseService =
            Room.databaseBuilder(context, DataBaseService::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context):DataBaseService =
            (instance ?: create(context = context).also { instance = it })
    }

    abstract fun noteDao(): NoteDao
}