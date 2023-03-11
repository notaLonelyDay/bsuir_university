package my.notalonelyday.bsuir.android.rssreader.ui.feed

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import de.charlex.compose.HtmlText
import my.notalonelyday.bsuir.android.rssreader.ui.webview.WebViewActivity
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Feed(
    viewModel: FeedViewModel = get()
) {

    val channelState = viewModel.channelFlow.collectAsState(null)
    val errorState = viewModel.errorFlow.collectAsState(null)

    val pullRefreshState = rememberPullRefreshState(
        viewModel.isRefreshing,
        onRefresh = {
            viewModel.updateChannel()
        }
    )


    Box(
        Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
            .padding(
                horizontal = 15.dp
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = errorState.value ?: ""
        )
        val mContext = LocalContext.current
        val openFileLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { result ->
            result ?: return@rememberLauncherForActivityResult
            val item = mContext.contentResolver.openInputStream(result)
                ?: return@rememberLauncherForActivityResult
            val bytes = item.readBytes()
            println(bytes)
            item.close()
            try {
                viewModel.setFromFile(bytes.decodeToString())
            } catch (_: Exception) {
            }
        }
        LazyColumn {

            item {
                TextField(
                    value = viewModel.channelUrl.value,
                    onValueChange = {
                        viewModel.setUrl(it)
                    }
                )
            }

            item {
                Button(
                    onClick = {
                        openFileLauncher.launch("*/*")
                    }
                ) {
                    Text("Pick file")
                }
            }

            channelState.value?.let { ChannelScreen(it) }
        }
        PullRefreshIndicator(
            state = pullRefreshState,
            refreshing = viewModel.isRefreshing,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.updateChannel()
    }

}

fun LazyListScope.ChannelScreen(
    channel: Channel
) {
    item {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = channel.image?.url,
                contentDescription = null,
                modifier = Modifier.size(
                    50.dp
                )
            )
            Text(
                text = channel.title ?: "",
                fontSize = 29.sp
            )
        }
        Text(
            text = channel.description ?: "",
            fontSize = 23.sp
        )
    }
    items(
        items = channel.articles
    ) { article ->
        val mContext = LocalContext.current
        FeedItem(
            article,
            modifier = Modifier.padding(
                horizontal = 0.dp,
                vertical = 30.dp
            )
                .clickable {
                    handleUri(mContext, article.link)
                }
        )
    }
}

@Composable
fun FeedItem(
    article: Article,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = article.title ?: "",
            fontSize = 23.sp
        )
        val mContext = LocalContext.current
        val uriHandler = remember {
            object : UriHandler {
                override fun openUri(uri: String) {
                    handleUri(mContext, uri)
                }
            }
        }
        CompositionLocalProvider(LocalUriHandler provides uriHandler) {
            article.description?.let {
                HtmlText(
                    text = it,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                )
            }
        }
        article.pubDate?.let {
            Text(
                text = it,
                fontSize = 15.sp
            )
        }
        article.author?.let {
            Text(
                text = it,
                fontSize = 14.sp
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                items = article.categories,
            ) {
                Text(
                    it,
                    modifier = Modifier
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(13.dp)
                        )
                        .padding(
                            vertical = 2.dp,
                            horizontal = 5.dp
                        ),
                    fontSize = 14.sp
                )
            }
        }
    }
}

fun handleUri(mContext: Context, uri: String?) {
    mContext.startActivity(
        WebViewActivity.create(
            mContext,
            uri
        )
    )
}