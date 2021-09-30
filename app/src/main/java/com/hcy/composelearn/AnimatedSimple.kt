package com.hcy.composelearn

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author hcy
 * @date 上午10:33
 * description： Compose 动画部分simeple代码
 */
class AnimatedSimple {
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun animateSimple() {
    var editable by remember { mutableStateOf(true) }
    val state = remember {
        MutableTransitionState(false)
            .apply { targetState = true }

    }
    val density = LocalDensity.current


    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
    ) {

        item {
            Box(Modifier.padding(5.dp)) {
                Button(onClick = {
                    editable = !editable
                    state.targetState
                }) {
                    Text(
                        text = if (editable) {
                            "隐藏"
                        } else {
                            "显示"
                        }
                    )
                }
            }

        }


//        AnimatedVisibility 简单的显示隐藏
        item {
            AnimatedVisibility(visible = editable) {
                Text(text = "AnimatedVisibility", Modifier.padding(5.dp))
            }
        }
//        指定EnterTransition 和 ExitrTransition
        item {
            AnimatedVisibility(
                visible = editable,

                enter = slideInVertically(
                    // Slide in from 40 dp from the top.
                    initialOffsetY = { with(density) { -40.dp.roundToPx() } }
                ) + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()

            ) {
                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.profile_picture),
                        contentDescription = "实例图片",
                        modifier = Modifier
                            .requiredSize(80.dp)
                            .align(Alignment.Center)
                    )

                    Text(
                        text = "指定EnterTransition 和 ExitrTransition",
                        Modifier
                            .padding(5.dp)
                            .align(
                                Alignment.Center
                            )
                    )
                }

            }


        }
//          MutableTransitionState 获取 AnimatedVisibility 的状态
        item {
            AnimatedVisibility(visibleState = state.apply { targetState = editable }) {
                Text(
                    text = " MutableTransitionState 获取 AnimatedVisibility 的状态",
                    Modifier.padding(5.dp)
                )
            }
        }
        item {
            Text(
                text = when {
                    state.isIdle && state.currentState -> "显示(初始状态)"
                    state.isIdle && !state.currentState -> "隐藏（新状态））"
                    !state.isIdle && state.currentState -> "Disappearing"
                    else -> "Appearing"
                },
                modifier = Modifier.padding(10.dp)
            )
        }

//        为子项添加进入和退出动画效果
        item {
            AnimatedVisibility(
                visible = editable,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.DarkGray)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .animateEnterExit(
                                enter = slideInVertically(),
                                exit = slideOutVertically()
                            )
                            .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                            .background(Color.Red)
                    ) {

                        Text(text = "这里是子项")

                    }

                    Text(text = "这里父项", color = Color.White)
                }


            }

        }

//        添加自定义动画
        item {
            AnimatedVisibility(
                visible = editable,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                val backgroud by transition.animateColor { state ->
                    if (state == EnterExitState.Visible) Color.Blue else Color.Gray
                }
                Box(
                    modifier = Modifier
                        .size(128.dp)
                        .background(backgroud)
                )
            }
        }

//        AnimatedContent 可组合项在内容根据目标状态发生变化时，添加内容的动画效果。
        item {
            Row() {
                var count by remember {
                    mutableStateOf(0)
                }
                Button(onClick = { count++ }) {
                    Text(text = "Add")
                }
                AnimatedContent(targetState = count) { targetCount ->
                    Text(text = "Count:${targetCount}", Modifier.padding(10.dp))
                }

            }
        }


        item {
            var count by remember {
                mutableStateOf(0)
            }
            Column() {
                AnimatedContent(targetState = count,
                    transitionSpec = {
                        // Compare the incoming number with the previous number.
                        if (targetState > initialState) {
                            // If the target number is larger, it slides up and fades in
                            // while the initial (smaller) number slides up and fades out.
                            slideInVertically({ height -> height }) + fadeIn() with
                                    slideOutVertically({ height -> -height }) + fadeOut()
                        } else {
                            // If the target number is smaller, it slides down and fades in
                            // while the initial number slides down and fades out.
                            slideInVertically({ height -> -height }) + fadeIn() with
                                    slideOutVertically({ height -> height }) + fadeOut()
                        }.using(
                            // Disable clipping since the faded slide-in/out should
                            // be displayed out of bounds.
                            SizeTransform(clip = false)
                        )
                    }
                ) { targetCount ->
                    Text(text = "$targetCount")
                }
                Row() {
                    Button(onClick = { count++ }) {
                        Text(text = "+")
                    }
                    Button(onClick = { count-- }) {
                        Text(text = "-")
                    }
                }
            }
        }

        item {
            var expanded by remember { mutableStateOf(false) }
            Surface(
                color = MaterialTheme.colors.surface,
//                onClick = { expanded = !expanded }
            ) {
                AnimatedContent(
                    targetState = expanded,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(150, 150)) with
                                fadeOut(animationSpec = tween(150)) using
                                SizeTransform { initialSize, targetSize ->
                                    if (targetState) {
                                        keyframes {
                                            // Expand horizontally first.
                                            IntSize(targetSize.width, initialSize.height) at 150
                                            durationMillis = 300
                                        }
                                    } else {
                                        keyframes {
                                            // Shrink vertically first.
                                            IntSize(initialSize.width, targetSize.height) at 150
                                            durationMillis = 300
                                        }
                                    }
                                }
                    }
                ) { targetExpanded ->
                    if (targetExpanded) {
                        Expanded() {
                            expanded = !expanded
                        }
                    } else {
                        ContentIcon() {
                            expanded = !expanded
                        }
                    }
                }
            }

        }
//        添加自定义动画
        item {
            var message by remember {
                mutableStateOf("hello")
            }
//            animateContentSize
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .animateContentSize()
            ) {
                Column {
                    Text(text = message.repeat(10), fontSize = 20.sp)
//                    TextField(value = message, onValueChange = {
//                        message = it
//                    })
                }
            }
        }

//            Crossfadel
        item {
            var currentPage by remember {
                mutableStateOf("A")
            }
            Column {
                Crossfade(targetState = currentPage) { screen ->
                    when (screen) {
                        "A" -> Text(text = "pageA")
                        "B" -> Text(text = "BBBBBBBpageB")
                    }
                }
                Row {
                    Button(onClick = { currentPage = "A" }) {
                        Text(text = "A")
                    }
                    Button(onClick = { currentPage = "B" }) {
                        Text(text = "B")
                    }
                }
            }


        }


    }
}


@Composable
fun ContentIcon(onclick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .size(40.dp),
        elevation = 2.dp
    ) {
        Box(
            Modifier
                .background(
                    Color(0xFF48D855)
                )
                .clickable { onclick() }

        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back), contentDescription = "icon",
                Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun Expanded(onclick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onclick() },
        elevation = 2.dp
    ) {
        Box(
            Modifier.background(
                Color(0xFF48D855)
            )
        ) {

            Text(
                text = "这是展开状态".repeat(20),
                color = Color.White,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showSystemUi = true)
@Composable
fun previewAnimateSimple() {
    animateSimple()
}
