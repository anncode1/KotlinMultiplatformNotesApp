package com.anncode.myfirstappmm.android.note_detail

data class NoteDetailState(
    val noteTitle: String = "",
    val isNoteTitleHintFocused: Boolean = false,
    val noteContent: String = "",
    val isNoteContentHintVisible: Boolean = false,
    val noteColor: Long = 0xFFFFFF
)
