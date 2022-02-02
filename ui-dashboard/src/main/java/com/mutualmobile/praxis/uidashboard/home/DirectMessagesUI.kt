package com.mutualmobile.praxis.uidashboard.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.mutualmobile.praxis.commonui.material.PraxisSurfaceAppBar
import com.mutualmobile.praxis.commonui.theme.PraxisSurface
import com.mutualmobile.praxis.commonui.theme.PraxisColorProvider
import com.mutualmobile.praxis.commonui.theme.PraxisTypography

@Composable
fun DirectMessagesUI() {
  PraxisSurface(color = PraxisColorProvider.colors.uiBackground, modifier = Modifier.fillMaxSize()){
    Column() {
      DMTopAppBar()
    }
  }
}

@Composable
fun DMTopAppBar() {
  PraxisSurfaceAppBar(
    title = {
      Text(text = "Direct Messages", style = PraxisTypography.h5.copy(color = Color.White, fontWeight = FontWeight.Bold))
    },
    backgroundColor = PraxisColorProvider.colors.appBarColor,
  )
}