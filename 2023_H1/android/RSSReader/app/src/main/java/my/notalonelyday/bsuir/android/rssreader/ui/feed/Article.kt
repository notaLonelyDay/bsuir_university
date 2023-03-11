package my.notalonelyday.bsuir.android.rssreader.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prof.rssparser.Article
import de.charlex.compose.HtmlText
import my.notalonelyday.bsuir.android.rssreader.ui.handleUri

@Composable
fun Article(
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
                            color = Color(0xFFD9D9D9),
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
