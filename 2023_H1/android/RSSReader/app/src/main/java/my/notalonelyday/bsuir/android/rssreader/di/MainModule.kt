package my.notalonelyday.bsuir.android.rssreader.di

import com.prof.rssparser.Parser
import my.notalonelyday.bsuir.android.rssreader.data.repository.AppRepo
import my.notalonelyday.bsuir.android.rssreader.data.repository.NetworkRepo
import my.notalonelyday.bsuir.android.rssreader.ui.feed.FeedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainModule = module {
    single {
        FeedViewModel(
            get()
        )
    }

    single<AppRepo> {
        NetworkRepo(
            get()
        )
    }

    single<Parser> {
        Parser.Builder()
            .context(get())
            .cacheExpirationMillis(24L * 60L * 60L * 1000L) // one day
            .build()

    }
}