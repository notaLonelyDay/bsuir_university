package my.notalonelyday.bsuir.android.rssreader.ui.feed

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof.rssparser.Channel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import my.notalonelyday.bsuir.android.rssreader.data.repository.AppRepo
import kotlin.coroutines.cancellation.CancellationException

class FeedViewModel(
    private val repo: AppRepo
) : ViewModel() {
    private val _channelFlow = MutableStateFlow<Channel?>(null)
    val channelFlow: Flow<Channel?> = _channelFlow

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow: Flow<String?> = _errorFlow


    private val _channelUrl = mutableStateOf("https://habr.com/ru/rss/all/")
    val channelUrl: State<String> = _channelUrl

    var isRefreshing by mutableStateOf(false)

    private var updateChannelJob: Job? = null
    fun updateChannel() {
        updateChannelJob?.cancel()
        updateChannelJob = viewModelScope.launch {
            isRefreshing = true
            try {
                _channelFlow.value = repo.getChannel(channelUrl.value)
                _errorFlow.value = null
            } catch (e: Exception) {
                if (e is CancellationException)
                    throw e
                _errorFlow.value = e.toString()
                _channelFlow.value = null
            } finally {
                isRefreshing = false
            }
        }
    }

    fun setUrl(url: String) {
        _channelUrl.value = url
        updateChannel()
    }

    fun setFromFile(rawRssFeed: String) {
        viewModelScope.launch {

            try {
                _channelFlow.value = repo.parseChannel(rawRssFeed)
                _errorFlow.value = null
            } catch (e: Exception) {
                _errorFlow.value = e.toString()
                _channelFlow.value = null
            } finally {

            }
        }
    }

}