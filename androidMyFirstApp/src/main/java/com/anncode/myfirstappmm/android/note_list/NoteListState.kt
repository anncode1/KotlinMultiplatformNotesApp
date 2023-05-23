package com.anncode.myfirstappmm.android.note_list

import com.anncode.myfirstappmm.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = String(),
    val isSearchActive: Boolean = false
)
