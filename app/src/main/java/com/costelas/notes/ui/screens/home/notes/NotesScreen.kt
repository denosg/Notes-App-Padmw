package com.costelas.notes.ui.screens.home.notes

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
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
import com.google.gson.Gson

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun Notes(
    viewModel: NotesViewModel,
    filter: String = "",
    navController: NavHostController
) {
    val notes = viewModel.notes.value

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
            items(notes.filter { note ->
                note.title.contains(filter, ignoreCase = true) || note.text.contains(filter, ignoreCase = true)
            }) { note ->
                val dismissState = rememberDismissState(
                    confirmStateChange = { dismissValue ->
                        if (dismissValue == DismissValue.DismissedToStart || dismissValue == DismissValue.DismissedToEnd) {
                            viewModel.removeNote(note.uid)
                            true
                        } else false
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background = {},
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart)
                ) {
                    NoteBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        note = note,
                        onClick = {
                            val data = Uri.encode(Gson().toJson(note))
                            navController.navigate(Screens.Edit.route + "?note=$data")
                        }
                    )
                }
            }
        }
    }
}