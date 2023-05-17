package com.anncode.myfirstappmm.domain.note

import com.anncode.myfirstappmm.presentation.*
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(
            RedOrangeHex,
            RedPinkHex,
            LightGreenHex,
            BabyBlueHex,
            VioletHex
        )

        fun generateRandomColor(): Long = colors.random()
    }
}
