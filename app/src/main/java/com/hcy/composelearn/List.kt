package com.hcy.composelearn

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * @author 侯程月
 * @date 17:19
 * description： 列表部分
 */
class List {


}


@Composable
fun MessageList(msg: ArrayList<Message>) {
 Column() {
  msg.forEach {
   MessageCard(msg = it)
  }
 }

}

@Preview
@Composable
fun textMessageList() {
 MessageList(
  msg = arrayListOf(
   Message("1", "body1"),
   Message("2", "body2"),
   Message("3", "body3"),
   Message("4", "body4")
  )
 )
}
