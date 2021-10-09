package com.hcy.composelearn

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
fun AnimateSimple() {
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
// 自定义动画
//        AnimationSpec
        item {
            val alpha: Float by animateFloatAsState(
                targetValue =
                if (editable) 1f else 0.5f,
//            Configure the animation duration and easing
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
//          spring
            val value by animateFloatAsState(
                targetValue = 1f,
                animationSpec = spring(
//                    定义弹簧弹性，默认值Spring.DampingRatioNoBouncy
                    dampingRatio = Spring.DampingRatioHighBouncy,
//                    定义弹簧应向结束移动速度，默认值为Spring.StiffnessMedium
                    stiffness = Spring.StiffnessMedium
                )
            )
//            tween 缓和曲线起始值和结束值之间添加动画效果
            val value1 by animateFloatAsState(
                targetValue =
                1f,
                animationSpec = tween(
                    durationMillis = 300,
                    delayMillis = 50,
                    easing = LinearOutSlowInEasing
                )
            )
//            keyframes 关键帧动画
            val value2 by animateFloatAsState(targetValue = 1f,
                animationSpec = keyframes {
                    durationMillis = 375
                    0.0f at 0 with LinearOutSlowInEasing//0-15ms
                    0.2f at 15 with FastOutSlowInEasing
                    0.4f at 75
                    0.4f at 255
                })
//            repeatable 反复运行动画
            val value3 by animateFloatAsState(
                targetValue = 1f,
                animationSpec = repeatable(
                    iterations = 3,
                    animation = tween(durationMillis = 300),
                    repeatMode = RepeatMode.Reverse
                )
            )
//            infiniteRepeatable 与 repeatable 类，但是会重复无限次
            val value4 by animateFloatAsState(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 300),
                    repeatMode = RepeatMode.Reverse
                )
            )
//            snap 立即将值切换到结束值
            val value5 by animateFloatAsState(
                targetValue = 1f,
                animationSpec = snap(delayMillis = 50)
            )


//            Easing 基于时长的 AnimationSpec 操作（如 tween 或 keyframes）使用 Easing 来调整动画的小数值。
//            这样可让动画值加速和减速，而不是以恒定的速率移动。小数是介于 0（起始值）和 1.0（结束值）之间的值，表示动画中的当前点。
            val CustomEasing = Easing { fraxtion ->
                fraxtion * fraxtion
            }
            EasingUsage(easing = CustomEasing)

//            AnimationVector
//            但有时您需要为其他数据类型（包括您的自定义类型）添加动画效果。在动画播放期间，任何动画值都表示为 AnimationVector。
//            使用相应的 TwoWayConverter 即可将值转换为 AnimationVector，反之亦然，这样一来，核心动画系统就可以统一对其进行处理

            val IntToVector: TwoWayConverter<Int, AnimationVector1D> =
                TwoWayConverter({
                    AnimationVector1D(it.toFloat())
                }, {
                    it.value.toInt()
                })


        }


    }
}

data class MySize(val width: Dp, val height: Dp)

@Composable
fun MyAnimation(targetSize: MySize) {
    val animSize: MySize by animateValueAsState(
        targetSize,
        TwoWayConverter(
            convertToVector = { size: MySize ->
                // Extract a float value from each of the `Dp` fields.
                AnimationVector2D(size.width.value, size.height.value)
            },
            convertFromVector = { vector: AnimationVector2D ->
                MySize(vector.v1.dp, vector.v2.dp)
            }
        )
    )
}


@Composable
fun EasingUsage(easing: Easing) {
    val value by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 300,
            easing = easing
        )
    )

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
    AnimateSimple()
}
