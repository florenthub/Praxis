package com.mutualmobile.praxis.uidashboard.compose

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.mutualmobile.praxis.commonui.theme.*
import com.mutualmobile.praxis.commonui.reusable.PraxisDragComposableView
import com.mutualmobile.praxis.navigator.ComposeNavigator
import com.mutualmobile.praxis.uichat.models.ChatPresentation
import com.mutualmobile.praxis.uichat.ChatScreenUI
import com.mutualmobile.praxis.uidashboard.home.*

@Composable
fun DashboardUI(composeNavigator: ComposeNavigator) {
  val scaffoldState = rememberScaffoldState()
  val dashboardNavController = rememberNavController()

  PraxisTheme {
    DashboardScreenRegular(scaffoldState, dashboardNavController)
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DashboardScreenRegular(
  scaffoldState: ScaffoldState,
  dashboardNavController: NavHostController
) {
  val keyboardController = LocalSoftwareKeyboardController.current

  var lastChannel by remember {
    mutableStateOf<ChatPresentation.PraxisChannel?>(null)
  }
  var isLeftNavOpen by remember { mutableStateOf(false) }
  var isChatViewClosed by remember { mutableStateOf(true) }
  val screenWidth = LocalConfiguration.current.screenWidthDp.dp
  val sideNavWidth = screenWidth * 0.8f
  val sideNavPxValue = with(LocalDensity.current) { sideNavWidth.toPx() }
  val screenWidthPxValue = with(LocalDensity.current) { screenWidth.toPx() }

  BackHandler(enabled = !isChatViewClosed){
    if(!isChatViewClosed){
      isChatViewClosed = true
    }
  }


  SideEffect {
    if (isChatViewClosed) {
      keyboardController?.hide()
    }
  }

  PraxisDragComposableView(
    isLeftNavOpen = isLeftNavOpen,
    isChatViewClosed = checkChatViewClosed(lastChannel, isChatViewClosed),
    mainScreenOffset = sideNavPxValue,
    chatScreenOffset = screenWidthPxValue,
    onOpenCloseLeftView = {
      isLeftNavOpen = it
    },
    onOpenCloseRightView = {
      isChatViewClosed = it
    },
    { modifier ->
      DashboardScaffold(
        isLeftNavOpen,
        scaffoldState,
        dashboardNavController,
        modifier,
        {
          isLeftNavOpen = !isLeftNavOpen
        }) {
        lastChannel = it
        isChatViewClosed = false
      }
    }, { leftViewModifier ->
      SideNavigation(leftViewModifier.width(sideNavWidth))
    }
  ) { chatViewModifier ->
    lastChannel?.let { slackChannel ->
      ChatScreenUI(chatViewModifier, slackChannel, {
        isChatViewClosed = true
      })
    }
  }
}

private fun checkChatViewClosed(
  lastChannel: ChatPresentation.PraxisChannel?,
  isChatViewClosed: Boolean
) = lastChannel == null || isChatViewClosed

@Composable
private fun DashboardScaffold(
  isLeftNavOpen: Boolean,
  scaffoldState: ScaffoldState,
  dashboardNavController: NavHostController,
  modifier: Modifier,
  appBarIconClick: () -> Unit,
  onItemClick: (ChatPresentation.PraxisChannel) -> Unit,
) {
  Box(modifier) {
    Scaffold(
      backgroundColor = PraxisColorProvider.colors.uiBackground,
      contentColor = PraxisColorProvider.colors.textSecondary,
      modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding(),
      scaffoldState = scaffoldState,
      bottomBar = {
        DashboardBottomNavBar(dashboardNavController)
      },
      snackbarHost = {
        scaffoldState.snackbarHostState
      },
      floatingActionButton = {
        FloatingActionButton(onClick = {

        }, backgroundColor = Color.White) {
          Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null,
            tint = PraxisCloneColor
          )
        }
      }
    ) { innerPadding ->
      Box(modifier = Modifier.padding(innerPadding)) {
        PraxisSurface(
          color = Color.White,
          modifier = Modifier.fillMaxSize()
        ) {
          NavHost(
            dashboardNavController,
            startDestination = Screen.Home.route,
          ) {
            composable(Screen.Home.route) {
              HomeScreenUI(appBarIconClick, onItemClick)
            }
            composable(Screen.DMs.route) {
              DirectMessagesUI()
            }
            composable(Screen.Mentions.route) {
              MentionsReactionsUI()
            }
            composable(Screen.Search.route) {
              SearchMessagesUI()
            }
            composable(Screen.You.route) {
              UserProfileUI()
            }
          }
        }
      }
      if (isLeftNavOpen) {
        OverlayDark(appBarIconClick)
      }
    }
  }
}

@Composable
private fun OverlayDark(appBarIconClick: () -> Unit) {
  Box(
    Modifier
      .fillMaxSize()
      .clickable {
        appBarIconClick()
      }
      .background(Color.Black.copy(alpha = 0.4f))
  ) {

  }
}

sealed class Screen(val route: String, val image: ImageVector, @StringRes val resourceId: Int) {
  object Home : Screen("Home", Icons.Filled.Home, com.mutualmobile.praxis.uidashboard.R.string.home)
  object DMs : Screen("DMs", Icons.Filled.Menu, com.mutualmobile.praxis.uidashboard.R.string.dms)
  object Mentions :
    Screen("Mentions", Icons.Filled.Email, com.mutualmobile.praxis.uidashboard.R.string.mentions)

  object Search :
    Screen("Search", Icons.Filled.Search, com.mutualmobile.praxis.uidashboard.R.string.search)

  object You : Screen("You", Icons.Default.Face, com.mutualmobile.praxis.uidashboard.R.string.you)

}

@Composable
fun DashboardBottomNavBar(navController: NavHostController) {
  Column(Modifier.background(color = PraxisColorProvider.colors.uiBackground)) {
    Divider(
      color = PraxisColorProvider.colors.textPrimary.copy(alpha = 0.2f),
      thickness = 0.5.dp
    )
    BottomNavigation(backgroundColor = PraxisColorProvider.colors.uiBackground) {
      val navBackStackEntry by navController.currentBackStackEntryAsState()
      val currentDestination = navBackStackEntry?.destination
      val dashTabs = getDashTabs()
      dashTabs.forEach { screen ->
        BottomNavItem(screen, currentDestination, navController)
      }
    }
  }
}

@Composable
private fun RowScope.BottomNavItem(
  screen: Screen,
  currentDestination: NavDestination?,
  navController: NavHostController,
) {

  BottomNavigationItem(
    selectedContentColor = PraxisColorProvider.colors.bottomNavSelectedColor,
    unselectedContentColor = PraxisColorProvider.colors.bottomNavUnSelectedColor,
    icon = { Icon(screen.image, contentDescription = null) },
    label = {
      Text(
        stringResource(screen.resourceId),
        maxLines = 1,
        style = PraxisTypography.overline,
      )
    },
    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
    onClick = {
      navigateTab(navController, screen)
    }
  )
}

private fun navigateTab(
  navController: NavHostController,
  screen: Screen
) {
  navController.navigate(screen.route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(navController.graph.findStartDestination().id) {
      saveState = true
    }
    // Avoid multiple copies of the same destination when
    // reselecting the same item
    launchSingleTop = true
    // Restore state when reselecting a previously selected item
    restoreState = true
  }
}

private fun getDashTabs(): MutableList<Screen> {
  return mutableListOf<Screen>().apply {
    add(Screen.Home)
    add(Screen.DMs)
    add(Screen.Mentions)
    add(Screen.Search)
    add(Screen.You)
  }
}

