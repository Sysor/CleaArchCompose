package ru.sysor.core.repository

import ru.sysor.core.data.Note

interface NoteDataSource {
    suspend fun add(note: Note)
    suspend fun get(id: Long) : Note?
    suspend fun getAll(): List<Note>
    suspend fun remove(note: Note)
}