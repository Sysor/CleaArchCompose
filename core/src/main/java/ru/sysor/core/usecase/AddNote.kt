package ru.sysor.core.usecase

import ru.sysor.core.data.Note
import ru.sysor.core.repository.NoteRepository

class AddNote(private val noteRepository : NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}