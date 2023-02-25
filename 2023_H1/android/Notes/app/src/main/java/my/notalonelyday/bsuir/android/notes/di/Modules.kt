@file:OptIn(ExperimentalSerializationApi::class)

package my.notalonelyday.bsuir.android.notes.di

import androidx.room.Room
import kotlinx.serialization.ExperimentalSerializationApi
import my.notalonelyday.bsuir.android.notes.data.db.NotesDatabase
import my.notalonelyday.bsuir.android.notes.data.repo.DBNotesRepository
import my.notalonelyday.bsuir.android.notes.data.repo.FSNotesRepository
import my.notalonelyday.bsuir.android.notes.data.repo.NotesRepository
import my.notalonelyday.bsuir.android.notes.data.repo.RepoProvider
import my.notalonelyday.bsuir.android.notes.ui.details.DetailsViewModel
import my.notalonelyday.bsuir.android.notes.ui.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainModule = module {
    viewModel { ListViewModel(get()) }
    viewModel { DetailsViewModel(get()) }

    single {
        RepoProvider(
            get(),
            get()
        )
    }
}

val roomModule = module {

    factory {
        DBNotesRepository(get())
    }

    single {
        Room.databaseBuilder(
            get(),
            NotesDatabase::class.java,
            "notes-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<NotesDatabase>().noteDao()
    }
}

val fsModule = module {
    factory {
        FSNotesRepository(get())
    }
}