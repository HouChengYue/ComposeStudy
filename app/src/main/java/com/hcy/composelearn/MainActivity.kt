package com.hcy.composelearn

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hcy.composelearn.data.SampleData
import com.hcy.composelearn.ui.theme.ComposeLearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            ComposeLearnTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
//            ComposeLearnTheme() {
//                MessageCard(
//                    msg = Message(
//                        "Cooleague",
//                        "Hey, take a kook at Jetpack Compose, it's great!"
//                    )
//                )
//            }
            ComposeLearnTheme {
//                Conversation(messages = SampleData.conversationSample)

                HellowScreen(viewModel())

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLearnTheme {
        Greeting("Android")
    }
}

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isEzpanded by remember {
            mutableStateOf(false)
        }


        val sufaceColor: Color by animateColorAsState(
            if (isEzpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )


        Column(
            modifier = Modifier.clickable {
                isEzpanded = !isEzpanded
            }
        ) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp,
            color = sufaceColor,
            modifier = Modifier
                .animateContentSize()
                .padding(1.dp)) {
                Text(
                    text = msg.body,
                    maxLines = if (isEzpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(all = 4.dp)
                )

            }
        }
    }


}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
)
@Composable
fun PreviewMessageCard() {
    ComposeLearnTheme() {
        MessageCard(
            msg = Message(
                "Cooleague",
                "Hey, take a kook at Jetpack Compose, it's great!"
            )
        )
    }

}

data class Message(val author: String, val body: String)

@Composable
fun Conversation(messages: ArrayList<Message>) {
    LazyColumn() {
        items(messages) { item ->
            MessageCard(msg = item)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeLearnTheme() {
        Conversation(messages = SampleData.conversationSample)
    }
}
