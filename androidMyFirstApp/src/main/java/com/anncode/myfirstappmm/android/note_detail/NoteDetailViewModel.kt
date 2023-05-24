package com.anncode.myfirstappmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anncode.myfirstappmm.domain.note.Note
import com.anncode.myfirstappmm.domain.note.NoteDataSource
import com.anncode.myfirstappmm.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val noteTitle = saveStateHandle.getStateFlow("noteTitle", "")
    private val isNoteTitleFocused = saveStateHandle.getStateFlow("isNoteTitleFocused", false)
    private val noteContent = saveStateHandle.getStateFlow("noteContent", "")
    private val isNoteContentFocused = saveStateHandle.getStateFlow("isNoteContentFocused", false)
    private val noteColor = saveStateHandle.getStateFlow(
        "noteColor",
        Note.generateRandomColor()
    )

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintFocused = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null


    init {
        saveStateHandle.get<Long>("noteId")?.let { existingNoteId ->

            if (existingNoteId == -1L) {
                return@let
            }

            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    saveStateHandle["noteTitle"] = note.title
                    saveStateHandle["noteContent"] = note.content
                    saveStateHandle["noteColor"] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        saveStateHandle["noteTitle"] = text
    }

    fun onNoteContentChanged(text: String) {
        saveStateHandle["noteContent"] = text
    }

    fun onNoteTitleFocusedChanged(isFocused: Boolean) {
        saveStateHandle["isNoteTitleFocused"] = isFocused
    }

    fun onNoteContentFocusedChanged(isFocused: Boolean) {
        saveStateHandle["isNoteContentFocused"] = isFocused
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }
}