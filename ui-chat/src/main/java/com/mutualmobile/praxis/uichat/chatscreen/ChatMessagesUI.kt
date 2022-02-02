package com.mutualmobile.praxis.uichat.chatscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mutualmobile.praxis.commonui.theme.PraxisColorProvider
import com.mutualmobile.praxis.commonui.theme.PraxisTypography
import com.mutualmobile.praxis.uichat.ChatThreadVM

@Composable
fun ChatMessagesUI(viewModel: ChatThreadVM, modifier: Modifier) {
  val messages = viewModel.chatMessagesFlow?.collectAsLazyPagingItems()
  val listState = rememberLazyListState()

  LazyColumn(state = listState, reverseLayout = true, modifier = modifier) {
    messages?.let {
      items(messages) { message ->
        message?.let {
          Column {
            ChatHeader(message.createdDate)
            ChatMessage(message)
          }
        }
      }
    }

  }
}

@Composable
private fun ChatHeader(createdDate: Long) {
  Column(Modifier.padding(start = 8.dp, end = 8.dp)) {
    Text(
      createdDate.calendar().formattedMonthDate(),
      style = PraxisTypography.subtitle2.copy(
        fontWeight = FontWeight.Bold,
        color = PraxisColorProvider.colors.textPrimary
      ), modifier = Modifier.padding(4.dp)
    )
    Divider(color = PraxisColorProvider.colors.lineColor, thickness = 0.5.dp)
  }
}
