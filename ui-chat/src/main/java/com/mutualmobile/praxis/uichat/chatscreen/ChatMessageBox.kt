
package com.mutualmobile.praxis.uichat.chatscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.mutualmobile.praxis.commonui.keyboard.Keyboard
import com.mutualmobile.praxis.commonui.keyboard.keyboardAsState
import com.mutualmobile.praxis.commonui.theme.PraxisColorProvider
import com.mutualmobile.praxis.commonui.theme.PraxisTypography
import com.mutualmobile.praxis.uichat.BoxState
import com.mutualmobile.praxis.uichat.ChatThreadVM

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatMessageBox(viewModel: ChatThreadVM, modifier: Modifier) {
  val keyboard by keyboardAsState()

  SideEffect {
    if (keyboard is Keyboard.Closed) {
      viewModel.chatBoxState.value = BoxState.Collapsed
    }
  }

  Column(
    modifier.background(PraxisColorProvider.colors.uiBackground),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    MessageTFRow(
      viewModel,
      modifier = Modifier.padding(
        start = 4.dp
      )
    )
    if (keyboard is Keyboard.Opened) {
      ChatOptions(
        viewModel,
        Modifier
      )
    }
  }

}

@Composable
fun ChatOptions(viewModel: ChatThreadVM, modifier: Modifier = Modifier) {
  val search by viewModel.message.collectAsState()

  Row(
    modifier
      .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(modifier = Modifier.weight(1f)) {
      IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.Add, contentDescription = null, chatOptionIconSize())
      }
      IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.AccountCircle, contentDescription = null, chatOptionIconSize())
      }
      IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.Email, contentDescription = null, chatOptionIconSize())
      }
      IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.ShoppingCart, contentDescription = null, chatOptionIconSize())
      }
      IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.Phone, contentDescription = null, chatOptionIconSize())
      }
      IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.MailOutline, contentDescription = null, chatOptionIconSize())
      }
    }
    Box(Modifier.padding(end = 8.dp)) {
      SendMessageButton(viewModel = viewModel, search = search)
    }
  }
}

private fun chatOptionIconSize() = Modifier.size(20.dp)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MessageTFRow(
  viewModel: ChatThreadVM,
  modifier: Modifier
) {
  val keyboard by keyboardAsState()

  val search by viewModel.message.collectAsState()
  Column {
    Divider(color = PraxisColorProvider.colors.lineColor, thickness = 0.5.dp)
    Row(
      modifier
    ) {
      BasicTextField(
        value = search,
        maxLines = 4,
        cursorBrush = SolidColor(PraxisColorProvider.colors.textPrimary),
        onValueChange = {
          viewModel.message.value = it
        },
        textStyle = PraxisTypography.subtitle1.copy(
          color = PraxisColorProvider.colors.textPrimary,
        ),
        decorationBox = { innerTextField ->
          ChatTFPlusPlaceHolder(search, Modifier, innerTextField)
        },
        modifier = Modifier.weight(1f)
      )

      if (keyboard is Keyboard.Closed) {
        SendMessageButton(viewModel, search)
      } else {
        CollapseExpandButton(viewModel)
      }
    }
  }

}

@Composable
fun CollapseExpandButton(viewModel: ChatThreadVM) {
  val isExpanded by viewModel.chatBoxState.collectAsState()
  IconButton(
    onClick = {
      viewModel.chatBoxState.value =
        if (viewModel.chatBoxState.value == BoxState.Collapsed) BoxState.Expanded else BoxState.Collapsed
    },
  ) {
    Icon(
      if (isExpanded == BoxState.Collapsed) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
      contentDescription = null,
    )
  }
}

@Composable
private fun SendMessageButton(
  viewModel: ChatThreadVM,
  search: String,
  modifier: Modifier = Modifier
) {
  IconButton(
    onClick = {
      viewModel.sendMessage(search)
    }, enabled = search.isNotEmpty(), modifier = modifier
  ) {
    Icon(
      Icons.Default.Send,
      contentDescription = null,
      tint = if (search.isEmpty()) PraxisColorProvider.colors.sendButtonDisabled else PraxisColorProvider.colors.sendButtonEnabled
    )
  }
}

@Composable
private fun ChatTFPlusPlaceHolder(
  search: String,
  modifier: Modifier = Modifier,
  innerTextField: @Composable () -> Unit
) {
  Row(
    modifier
      .padding(16.dp), verticalAlignment = Alignment.CenterVertically
  ) {
    if (search.isEmpty()) {
      Text(
        text = "Message #jetpack_compose",
        style = PraxisTypography.subtitle1.copy(
          color = PraxisColorProvider.colors.textSecondary,
        ),
        modifier = Modifier.weight(1f)
      )
    } else {
      innerTextField()
    }
  }
}




