package com.anncode.myfirstappmm.domain.note

import com.anncode.myfirstappmm.domain.time.DateTimeUtil

class SearchNotes {
    operator fun invoke(notes: List<Note>, query: String): List<Note> {
        if (query.isBlank()) return notes

        return notes.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
                    it.content.trim().lowercase().contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpochMilis(it.created)
        }
    }
}