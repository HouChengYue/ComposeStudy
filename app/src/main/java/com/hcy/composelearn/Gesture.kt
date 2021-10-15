package com.hcy.composelearn

import android.content.ContentValues.TAG
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

/**
 * @author 侯程月
 * @date 10:58
 * description：手势部分Demo
 */
object Gesture {
}


@Preview
@Composable
fun PreviewClickableSample() {
    ClickableSample()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClickableSample() {
    val spacer = Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
    LazyColumn {
        item {
            click()
        }
        item {
            ScrollBoxes()
        }
        item {
            spacer
        }
        item {
            ScrollableSample()
        }
        item {
            spacer
        }
//        item {
//        不能添加在这里，与Column 冲突
//            NestedScrollSample()
//        }
        item {
//            拖拽
            draggableSimple()
        }
        item {
//            拖拽状态
            slideSimple()
        }
//      多指触控
        item {
            TransformableSample()
        }

    }
}

fun <T> T.loge(msg: String) {
    android.util.Log.e(TAG, "Loge: $msg")
}

@Composable
fun click() {
    val count = remember { mutableStateOf(0) }
    Box(
        Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        loge("onPress")
                        count.value++
                    },
                    onDoubleTap = {
                        loge("onDoubleTap")
                    },
                    onLongPress = {
                        loge("onLongPress")
                    },
                    onTap = {
                        loge("onTap")
                    }

                )
            }
            .height(44.dp)
            .fillMaxWidth()
//            .clickable { count.value++ }
    )
    {
        Text(
            text = count.value.toString(),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ScrollBoxes() {
    val state = rememberScrollState()
    LaunchedEffect(key1 = Unit) {
        state.animateScrollTo(500)
    }
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .height(100.dp)
            .fillMaxWidth()
            .verticalScroll(state)
    ) {
        repeat(10) {
            Text(text = "这是条条目$it", modifier = Modifier.padding(2.dp))
        }
    }
}

@Composable
fun ScrollableSample() {
    var offset by remember {
        mutableStateOf(0f)
    }
    Box(
        Modifier
            .height(150.dp)
            .fillMaxWidth()
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                }
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = offset.toString())
    }
}

@Composable
fun NestedScrollSample() {
    val gradient = Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White)
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        "Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(brush = gradient)
                            .padding(24.dp)
                            .height(150.dp)
                    )
                }
            }
        }
    }


}

@Composable
fun draggableSimple() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) {
        var offsetX by remember { mutableStateOf(100f) }
        var offsetY by remember { mutableStateOf(100f) }
        Box(
            Modifier
                .offset {
                    IntOffset(
                        offsetX.roundToInt(),
                        offsetY.roundToInt()
                    )
                }
                .background(Color.Blue)
                .size(60.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        ) {

        }

    }

}


@ExperimentalMaterialApi
@Composable
fun slideSimple() {
    val width = 96.dp
    val squareSize = 48.dp
    val swipeableSate = rememberSwipeableState(0)

    val sizePx = with(LocalDensity.current) {
        squareSize.toPx()
    }
    val anchors = mapOf(0f to 0, sizePx to 1)

    Box(
        modifier = Modifier
            .width(width = width)
            .swipeable(
                state = swipeableSate,
                anchors = anchors,
                thresholds = { _, _ ->
                    FractionalThreshold(0.3f)
                },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {

        Box(
            Modifier
                .offset {
                    IntOffset(
                        swipeableSate.offset.value.roundToInt(), 0
                    )
                }
                .size(squareSize)
                .background(Color.DarkGray)
        )


        {

        }


    }
}

//多指触控
@Composable
fun TransformableSample() {
    var scale by remember {
        mutableStateOf(1f)
    }

    var rotation by remember {
        mutableStateOf(0f)
    }

    var offset by remember {
//        mutableStateOf(Offset.Zero)
        mutableStateOf(Offset(100f,100f))
    }

    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(
        Modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            .transformable(state = state)
            .background(Color(0xFFECA947))
            .size(300.dp)
    ) {

    }


}





