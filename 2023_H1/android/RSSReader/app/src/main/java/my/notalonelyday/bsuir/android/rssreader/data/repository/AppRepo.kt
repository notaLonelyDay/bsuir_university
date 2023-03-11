package my.notalonelyday.bsuir.android.rssreader.data.repository

import com.prof.rssparser.Channel

interface AppRepo {
    suspend fun getChannel(url: String): Channel

    suspend fun parseChannel(rawXml: String): Channel
}