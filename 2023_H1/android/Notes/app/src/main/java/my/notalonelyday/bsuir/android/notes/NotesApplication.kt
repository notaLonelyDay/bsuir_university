package my.notalonelyday.bsuir.android.notes

import android.app.Application
import my.notalonelyday.bsuir.android.notes.di.fsModule
import my.notalonelyday.bsuir.android.notes.di.mainModule
import my.notalonelyday.bsuir.android.notes.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotesApplication)
            modules(mainModule + roomModule + fsModule)
        }
    }
}