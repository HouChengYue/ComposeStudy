package com.hcy.composelearn.ui.theme

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hcy.composelearn.R

/**
 * @author 侯程月
 * @date 13:37
 * description：
 */
class TextSimple {
}

@Composable
fun SimpleText() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        item {
            Text(text = "Hello World")
        }
        item {
            Text(text = stringResource(id = R.string.hello_world))
        }
        item {

            Text(text = "Hello World", color = Color.Blue)
        }
        item {
//  字体大小
            Text(text = "Hello World", color = Color.Blue, fontSize = 30.sp)
        }
        item {

//  字体斜体
            Text(
                text = "Hello World",
                color = Color.Cyan,
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic
            )
        }
        item {
//  字体加粗
            Text(
                text = "Hello World", color = Color.Gray, fontSize = 30.sp,
                fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold
            )

        }

        item {

//设置对齐
            Text(
                text = "Hello World", color = Color.Green, fontSize = 30.sp,
                fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
        item {

//  处理字体
            Text(
                text = "FontFamily.Cursive", color = Color.Magenta, fontSize = 30.sp,
                fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                fontFamily = FontFamily.Cursive
            )
        }
        item {
            Text(
                text = "FontFamily.Monospace", color = Color.Yellow, fontSize = 30.sp,
                fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                fontFamily = FontFamily.Monospace
            )
        }

        item {

//  一段多样式
            Text(modifier = Modifier.padding(10.dp), text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Blue, fontSize = 30.sp)) {
                    append("H")
                }
                append("ello")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                    append("W")
                }
                append("orld")
            })
        }
        item {
            //设置为段落
            Text(modifier = Modifier.padding(10.dp), text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Blue, fontSize = 30.sp)) {
                    append("H")
                }
                append("ello\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                    append("W")
                }
                append("orld\n")
                append("Composed")
            })
        }

        item {
//行数上线
            Text(
                text = "Deprecated Gradle features were used in this build, making it incompatible with Gradle 8.0." +
                        "Use '--warning-mode all' to show the individual deprecation warnings.Deprecated Gradle features were used in this build, making it incompatible with Gradle 8.0.\n" +
                        "Use '--warning-mode all' to show the individual deprecation warnings.\n",
                maxLines = 5
            )
        }
        item {
            //文字溢出
            Text(
                text = "Deprecated Gradle features were used in this build, making it incompatible with Gradle 8.0.\n" +
                        "Use '--warning-mode all' to show the individual deprecation warnings.Deprecated Gradle features were used in this build, making it incompatible with Gradle 8.0.\n" +
                        "Use '--warning-mode all' to show the individual deprecation warnings.",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        item {
            //  可选择文字
            SelectionContainer {
                Text(text = "可选择文字".repeat(100))
            }
        }
        item {
            //获取点击文字的位置
            ClickableText(text = AnnotatedString("这里可以点击".repeat(10)), onClick = { offset ->
                Log.d(TAG, "SimpleText: 第$offset 个字被点击了！")
            })
        }
        item {
            Column {
                //  点击注解
                Spacer(modifier = Modifier.height(10.dp))
                val annotatedText = buildAnnotatedString {
                    append("请点这里到百度")
                    pushStringAnnotation("URL", "https://www.baidu.com")
                    withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                        append("百度")
                    }
                    pop()
                }
                ClickableText(text = annotatedText, onClick = { offset ->
                    annotatedText.getStringAnnotations(
                        tag = "URL",
                        start = offset, end = offset
                    )
                        .firstOrNull()?.let { annotion ->
                            Log.d(TAG, "SimpleText: ${annotion.item}")
                        }

                })
//  输入和修改文字
                var input by remember {
                    mutableStateOf("")
                }
                TextField(value = input, onValueChange = {
                    input = it
                }, label = {
                    Text(text = "用户名")
                })
//outLinedTextField
                OutlinedTextField(value = input, onValueChange = {
                    input = it
                }, label = { Text(text = "OutlinedTextField") })
//设置TextField样式
                TextField(
                    value = input, onValueChange = { input = it },
                    label = { Text(text = "Enter Text") },
                    maxLines = 2,
                    textStyle = TextStyle(
                        color = Color(0xFF96CFFC),
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 8.dp)
                )
//格式设置
                var password by rememberSaveable {
                    mutableStateOf("")
                }
                TextField(
                    value = password, onValueChange = {
                        password = it
                    },
                    label = { Text(text = "输入密码！") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )


                )


            }
        }


    }
}

@Preview(showSystemUi = true)
@Composable
fun TextShow() {
    SimpleText()
}
