package my.notalonelyday.bsuir.android.notes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import my.notalonelyday.bsuir.android.notes.di.fsModule
import my.notalonelyday.bsuir.android.notes.di.roomModule
import my.notalonelyday.bsuir.android.notes.ui.details.DetailScreen
import my.notalonelyday.bsuir.android.notes.ui.list.CREATE_NOTE_ID
import my.notalonelyday.bsuir.android.notes.ui.list.ListScreen
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesApp() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = NotesDst.LIST.dstName
    ) {
        composable(NotesDst.LIST.dstName) {
            ListScreen(openNoteDetails = { navController.navigate("${NotesDst.DETAILS.dstName}/$it") })
        }
        composable(
            "${NotesDst.DETAILS.dstName}/{${NotesDst.DETAILS.Args.NOTE_ID}}",
            arguments = listOf(navArgument(NotesDst.DETAILS.Args.NOTE_ID) {
                type = NavType.LongType
                defaultValue = CREATE_NOTE_ID
            })
        ) {
            DetailScreen(noteId = it.arguments!!.getLong(NotesDst.DETAILS.Args.NOTE_ID),
                         closeDetailsScreen = { navController.popBackStack() })
        }
    }
}

sealed class NotesDst(
    val dstName: String,
) {
    object LIST : NotesDst("list")
    object DETAILS : NotesDst("details") {
        object Args {
            const val NOTE_ID = "note_id"
        }
    }
}