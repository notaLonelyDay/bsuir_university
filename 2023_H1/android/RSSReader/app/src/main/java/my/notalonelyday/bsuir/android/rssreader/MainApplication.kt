package my.notalonelyday.bsuir.android.rssreader

import android.app.Application
import my.notalonelyday.bsuir.android.rssreader.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            loadKoinModules(mainModule)
        }
    }
}