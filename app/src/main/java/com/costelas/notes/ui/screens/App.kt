package com.costelas.notes.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgument
import com.costelas.notes.common.models.Note
import com.costelas.notes.ui.SignInActivity
import com.costelas.notes.ui.navigation.Screens
import com.costelas.notes.ui.screens.edit.EditScreen
import com.costelas.notes.ui.screens.home.HomeScreen
import com.costelas.notes.ui.screens.settings.SettingsScreen
import com.costelas.notes.ui.screens.settings.SettingsViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.gson.Gson

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun App(
    user: FirebaseUser,
) {
    val viewModel: AppViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel();
    val navController = rememberAnimatedNavController()
    val context = LocalContext.current

    AnimatedNavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(viewModel, navController, user)
        }
        composable(
            Screens.Edit.route + "?note={data}",
            arguments = listOf(navArgument("data") {
                nullable = true
            })
        ) {
            val data = it.arguments?.getString("data")
            val note = Gson().fromJson(data, Note::class.java)
            EditScreen(navController, note)
        }
        composable(Screens.Settings.route) {
            SettingsScreen(
                navController = navController,
                viewModel = settingsViewModel,
                onThemeToggle = { isDark -> },
                onLogout = {
                    Firebase.auth.signOut()
                    context.startActivity(Intent(context, SignInActivity::class.java))
                    (context as? Activity)?.finish()
                }
            )
        }
    }
}