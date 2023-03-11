package my.notalonelyday.bsuir.android.rssreader.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.prof.rssparser.Channel
import my.notalonelyday.bsuir.android.rssreader.ui.handleUri

fun LazyListScope.Channel(
    channel: Channel
) {
    item {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = channel.image?.url,
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier.size(
                    50.dp
                )
                    .background(
                        color= Color(0xFFA2A2A2),
                        shape = RoundedCornerShape(20.dp)
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
        Article(
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
