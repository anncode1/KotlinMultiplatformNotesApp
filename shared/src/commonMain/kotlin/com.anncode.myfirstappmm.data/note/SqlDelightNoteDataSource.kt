package com.anncode.myfirstappmm.data.note

import com.anncode.myfirstappmm.database.NoteDatabase
import com.anncode.myfirstappmm.domain.note.Note
import com.anncode.myfirstappmm.domain.note.NoteDataSource
import com.anncode.myfirstappmm.domain.time.DateTimeUtil

class SqlDelightNoteDataSource(
    db: NoteDatabase
) : NoteDataSource {

    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            note.id,
            note.title,
            note.content,
            note.colorHex,
            DateTimeUtil.toEpochMilis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries
            .getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries
            .getAllNotes()
            .executeAsList()
            .map { it.toNote() }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }

}