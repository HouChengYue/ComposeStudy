package com.hcy.composelearn

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview

/**
 * @author hcy
 * @date 上午11:24
 * description：Compose 学习中的Iamge部分
 * https://developer.android.google.cn/jetpack/compose/graphics
 */
class Image {
}

@Composable
fun ImageSample(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val cWidth = size.width
        val cHeight = size.height
//        划线
        drawLine(
            start = Offset(x = cWidth, y = 0f),
            end = Offset(x = 0f, y = cHeight),
            color = Color.Blue,
//            设置线宽
            strokeWidth = 5F
        )
// 画园
        drawCircle(
            color = Color(0xFF6DC8F1),
            center = Offset(x = cWidth / 2, y = cHeight / 2),
            radius = size.minDimension / 4
        )
//画长方形
        val canasQuadranSize = size / 4F
        drawRect(
            color = Color(0xFF8BC34A),
            size = canasQuadranSize
        )
// 您可以使用 DrawScope.inset() 函数来调整当前作用域的默认参数
        inset(cWidth / 2, cHeight / 2) {
            drawRect(
                color = Color(0xFFFF9800),
                size = canasQuadranSize
            )
        }

//在画布中央绘制一个占据九分之一空间的矩形：
        drawRect(
            color = Color.Gray,
            topLeft = Offset(x = cWidth / 3f, y = cHeight / 3f),
            size = size / 3f
        )
//        旋转矩形
        rotate(degrees = 45F){
            drawRect(
                color = Color(0xFFA0A0A0),
                topLeft = Offset(x = cWidth / 3f, y = cHeight / 3f),
                size = size / 4f
            )
        }
//        移动旋转的优化操作
        withTransform({
            translate(top = cWidth/5f)
            rotate(degrees = 60f)
        }){
            drawRect(
                color = Color(0xFFBDBDBD),
                topLeft = Offset(x = cWidth / 3f, y = cHeight / 3f),
                size = size / 5f
                )

        }



    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewImageSample() {
    ImageSample()
}

