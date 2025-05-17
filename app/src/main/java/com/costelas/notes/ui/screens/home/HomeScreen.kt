package com.costelas.notes.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.costelas.notes.R
import com.costelas.notes.ui.components.SearchBar
import com.costelas.notes.ui.components.TitleText
import com.costelas.notes.ui.navigation.Screens
import com.costelas.notes.ui.screens.AppViewModel
import com.costelas.notes.ui.screens.home.notes.Notes
import com.costelas.notes.ui.screens.home.notes.NotesViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseUser

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    navController: NavHostController,
    user: FirebaseUser,
) {

    var searchQuery by remember { mutableStateOf("") }
    var isDialogVisible by remember { mutableStateOf(false) }

    val notesViewModel: NotesViewModel = hiltViewModel()
    val pagerState = rememberPagerState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.Edit.route) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add new note")
            }
        }
    ) { innerPadding ->
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleText(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(id = R.string.app_name)
                )
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier.padding(16.dp),
                )
                IconButton(onClick = {
                    navController.navigate(Screens.Settings.route)
                }) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp),
                        painter = rememberImagePainter(data = user.photoUrl),
                        contentDescription = "Profile Picture"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            SearchBar(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                onSearch = {},
                focusManager = focusManager
            )

            HorizontalPager(
                count = 2,
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) { page: Int ->
                when (page) {
                    0 -> Notes(viewModel = notesViewModel, navController = navController)
                    1 -> {}
                }
            }
        }

//        if (isDialogVisible) {
//            AlertDialog(
//                onDismissRequest = { isDialogVisible = false },
//                confirmButton = {
//                    TextButton(onClick = signOut) {
//                        Text(text = "Yes")
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = { isDialogVisible = false }) {
//                        Text(text = "No")
//                    }
//                },
//                title = {
//                    Text(text = "Sign out?")
//                },
//                backgroundColor = MaterialTheme.colors.background,
//                contentColor = MaterialTheme.colors.onBackground
//            )
//        }
    }
}