package ru.sysor.composecleanarch.framework

import android.content.Context
import ru.sysor.composecleanarch.framework.db.DataBaseService
import ru.sysor.composecleanarch.framework.db.NoteEntity
import ru.sysor.core.data.Note
import ru.sysor.core.repository.NoteDataSource

class RoomNoteDataSource(context: Context): NoteDataSource {
    private val noteDao = DataBaseService.getInstance(context = context).noteDao()

    override suspend fun add(note: Note) {
        noteDao.addNoteEntity(NoteEntity.fromNote(note))
    }

    override suspend fun get(id: Long): Note? = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll(): List<Note> {
        return noteDao.getAllNoteEntities().map { it.toNote() }
    }

    override suspend fun remove(note: Note) {
        noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
    }
}