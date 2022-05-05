package ru.sysor.composecleanarch.framework.db

import androidx.room.*
import androidx.room.OnConflictStrategy.*

const val TABLE_NAME = "note"

@Dao
interface NoteDao {
    @Insert(onConflict = REPLACE)
    suspend fun addNoteEntity(noteEntity: NoteEntity)

    @Update(onConflict = ABORT)
    suspend fun updateNoteEntry(noteEntity: NoteEntity)

    @Query("Select * from $TABLE_NAME where id = :id")
    suspend fun getNoteEntity(id: Long) : NoteEntity?

    @Query("select * from $TABLE_NAME")
    suspend fun getAllNoteEntities(): List<NoteEntity>

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)
}