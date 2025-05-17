package com.costelas.notes.common.models

import androidx.compose.ui.graphics.toArgb
import com.costelas.notes.ui.theme.*

data class Todo(
    val todo: String,
    val deadline: Long,
    val isDone:Boolean,
    val timeStamp: Long = System.currentTimeMillis(),
    val uid: String = System.currentTimeMillis().toString(),
) {
    companion object {
        val colors = listOf(
            NoteRed.toArgb(),
            NoteBlue.toArgb(),
            NoteYellow.toArgb(),
            NoteGreen.toArgb(),
            NoteViolet.toArgb(),
            NotePink.toArgb(),
            NoteOrange.toArgb()
        )
    }
}