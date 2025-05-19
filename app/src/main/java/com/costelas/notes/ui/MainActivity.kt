package com.costelas.notes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.costelas.notes.data.ThemeManager
import com.costelas.notes.ui.screens.App
import com.costelas.notes.ui.theme.NotesTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth
    @Inject lateinit var themeManager: ThemeManager

    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(themeManager.isDarkMode()) }

            NotesTheme(darkTheme = isDarkTheme) {
                Surface(color = MaterialTheme.colors.background) {
                    App(
                        user = auth.currentUser!!,
                        onToggleTheme = {
                            isDarkTheme = it
                            themeManager.setDarkMode(it)
                        }
                    )
                }
            }
        }
    }
}