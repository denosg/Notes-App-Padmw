package com.costelas.notes.ui.screens.got

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.costelas.notes.common.models.Note
import com.costelas.notes.common.models.Result
import com.costelas.notes.ui.navigation.Screens
import com.costelas.notes.ui.screens.edit.EditViewModel
import com.costelas.notes.ui.screens.home.inspiration.GoTViewModel
import com.google.gson.Gson
import java.util.*

@Composable
fun GoTScreen(
    navController: NavHostController,
    viewModel: GoTViewModel = hiltViewModel(),
    noteViewModel: EditViewModel = hiltViewModel(),
    onNoteCreated: (Note) -> Unit
) {
    val context = LocalContext.current
    val quoteState by viewModel.quote
    var dialogVisible by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchQuote()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp)) // Push content to upper-mid

            when (val result = quoteState) {
                is Result.Loading -> CircularProgressIndicator()
                is Result.Error -> Text("Failed to load quote: ${result.message}")
                is Result.Success -> {
                    val quote = result.data!!
                    val note = Note(
                        uid = UUID.randomUUID().toString(),
                        title = quote.sentence,
                        text = "\"${quote.character.name}\"\n\nHouse: ${quote.character.house.name}",
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedNote = note
                                dialogVisible = true
                            },
                        shape = RoundedCornerShape(16.dp),
                        elevation = 8.dp
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = "“${quote.sentence}”",
                                style = MaterialTheme.typography.h6
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("— ${quote.character.name}", style = MaterialTheme.typography.subtitle1)
                            Text("House ${quote.character.house.name}", color = Color.Gray)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(onClick = { viewModel.fetchQuote() }) {
                        Text("Refresh")
                    }
                }
            }
        }

        if (dialogVisible && selectedNote != null) {
            AlertDialog(
                onDismissRequest = { dialogVisible = false },
                title = { Text("Add Quote") },
                text = { Text("Do you want to add this quote to your notes?") },
                confirmButton = {
                    TextButton(onClick = {
                        dialogVisible = false
                        noteViewModel.initValues(selectedNote)
                        noteViewModel.saveNote(selectedNote?.uid) {
                            val data = Uri.encode(Gson().toJson(selectedNote))
                            navController.navigate(Screens.Edit.route + "?note=$data")
                        }
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { dialogVisible = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}