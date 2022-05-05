package ru.sysor.composecleanarch.framework

import ru.sysor.core.usecase.AddNote
import ru.sysor.core.usecase.GetAllNotes
import ru.sysor.core.usecase.GetNote
import ru.sysor.core.usecase.RemoveNote

data class UseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote
)