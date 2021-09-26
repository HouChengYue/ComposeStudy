package com.hcy.composelearn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcy.composelearn.data.SampleData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @author 侯程月
 * @date 17:19
 * description： 列表部分
 */
class List {


}


@OptIn(ExperimentalFoundationApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun MessageList(modifier: Modifier=Modifier,msg: ArrayList<Message>) {
    Box(modifier = modifier) {
        Spacer(modifier = Modifier.fillMaxWidth())
// Column(modifier=Modifier.fillMaxWidth()) {
//  msg.forEach {
//   MessageCard(msg = it)
//  }
// }
//    LazyColumn {
//        items(msg) {
//            MessageCard(msg = it)
//        }
//    }
//内容内边距
//    LazyColumn(contentPadding = PaddingValues(
//        horizontal = 16.dp,
//        vertical = 8.dp
//    )) {
//        items(msg) {
//            MessageCard(msg = it)
//        }
//    }
//内容间距
//    LazyColumn(
//        contentPadding = PaddingValues(
//            horizontal = 16.dp,
//            vertical = 8.dp
//        ),
//        verticalArrangement = Arrangement.spacedBy(6.dp)
//    ) {
//        items(msg) {
//            MessageCard(msg = it)
//        }
//    }
//粘性标题
//    LazyColumn(
//        contentPadding = PaddingValues(
//            horizontal = 16.dp,
//            vertical = 8.dp
//        ),
//        verticalArrangement = Arrangement.spacedBy(6.dp)
//    ) {
//        stickyHeader {
//            Header(text = "header")
//        }
//
//
//        items(msg) {
//            MessageCard(msg = it)
//        }
//    }

        val grouped = msg.groupBy {
            it.body[0].toString()
        }
        val listState = rememberLazyListState()

        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            state = listState

        ) {
            grouped.forEach { (k, v) ->
                stickyHeader {
                    Header(text = k)
                }
                items(v) {
                    MessageCard(msg = it)
                }
            }
        }
        val showButtom by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        AnimatedVisibility(visible = showButtom) {
            ScrollToTopButton(modifier.align(Alignment.BottomEnd)) {
                MainScope().launch {
                    listState.scrollToItem(0, 0)
                }
            }

        }
    }
}

@Composable
fun Header(text: String) {
    Text(text = text)
}

@Composable
fun ScrollToTopButton(modifier: Modifier=Modifier,onClick:()->Unit={}) {
    Box (modifier.fillMaxSize()){
        Surface(
            shape = RoundedCornerShape(20.dp),
            elevation = 2.dp,
            contentColor = Color.White,
            modifier = modifier
                .padding(10.dp)
                .size(40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_upward_24),
                contentDescription = "返回顶部",
                modifier = modifier.fillMaxSize().clickable { onClick() }
            )
        }
    }


}


@Preview(showBackground = true)
@Composable
fun textMessageList() {
    MessageList(
        msg = SampleData.conversationSample
    )
}




