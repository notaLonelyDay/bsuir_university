package my.notalonelyday.bsuir.android.rssreader.data.repository

import com.prof.rssparser.Channel
import com.prof.rssparser.Parser

class NetworkRepo(
    private val parser: Parser
): AppRepo {
    override suspend fun getChannel(url: String): Channel {
        parser.flushCache(url)
        return parser.getChannel(url)
    }

    override suspend fun parseChannel(rawRssFeed: String): Channel {
        return parser.parse(rawRssFeed)
    }
}