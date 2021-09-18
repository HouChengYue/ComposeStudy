package com.hcy.composelearn.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcy.composelearn.R

/**
 * @author hcy
 * @date 下午5:03
 * description：
 */


@Composable
fun PhotographerCard(modifier: Modifier=Modifier) {
    Column {
        Row(modifier
            .padding(16.dp)
            .clickable {  }
        ) {

            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "头像",
                    modifier = Modifier.size(50.dp),
                )
            }

            Column (
                modifier = Modifier.padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
                    ){
                Text("Alfred Sisley", fontWeight = FontWeight.Bold)
                // LocalContentAlpha is defining opacity level of its children
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text("3 minutes ago", style = MaterialTheme.typography.body2)
                }
            }
        }

        Column {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago")
            }

        }

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewPhotographerCard() {
    PhotographerCard()
}



