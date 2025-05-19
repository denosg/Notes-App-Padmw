package com.costelas.notes.ui.screens.home.notes

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.costelas.notes.R
import com.costelas.notes.ui.components.BodyText
import com.costelas.notes.ui.components.HeadingText
import com.costelas.notes.ui.components.NoteBox
import com.costelas.notes.ui.navigation.Screens
import com.costelas.notes.common.models.Note
import com.google.gson.Gson

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun Notes(
    viewModel: NotesViewModel,
    filter: String = "",
    navController: NavHostController
) {
    val notes = viewModel.notes.value

    var notePendingDelete by remember { mutableStateOf<Note?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (notes.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(48.dp),
                painter = painterResource(id = R.drawable.ic_illustration),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            HeadingText(text = "It's Empty!")
            BodyText(
                text = "Hmm.. looks like you don't \nhave any notes",
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val filteredNotes = notes.filter { note ->
                note.title.contains(filter, ignoreCase = true) || note.text.contains(filter, ignoreCase = true)
            }

            items(filteredNotes) { note ->
                key(note.uid) {
                    NoteBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        note = note,
                        onClick = {
                            val data = Uri.encode(Gson().toJson(note))
                            navController.navigate(Screens.Edit.route + "?note=$data")
                        },
                        onLongClick = {
                            notePendingDelete = note
                            showDialog = true
                        }
                    )
                }
            }
        }
    }

    if (showDialog && notePendingDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                notePendingDelete = null
            },
            title = {
                Text(text = "Delete Note?")
            },
            text = {
                Text("Are you sure you want to delete \"${notePendingDelete?.title}\"?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        notePendingDelete?.let { viewModel.removeNote(it.uid) }
                        showDialog = false
                        notePendingDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        notePendingDelete = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}