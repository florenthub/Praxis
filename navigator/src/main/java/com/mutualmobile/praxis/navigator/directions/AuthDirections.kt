package com.mutualmobile.praxis.navigator.directions

import androidx.navigation.NamedNavArgument

object AuthenticationDirections {

  val root = object : NavigationCommand {

    override val arguments = emptyList<NamedNavArgument>()

    override val destination = "connect"

  }

  val authentication  = object : NavigationCommand {

    override val arguments = emptyList<NamedNavArgument>()

    override val destination = "authentication"

  }

  val Default = object : NavigationCommand {

    override val arguments = emptyList<NamedNavArgument>()

    override val destination = ""

  }

  val dashboard = object : NavigationCommand {

    override val arguments = emptyList<NamedNavArgument>()

    override val destination = "dashboard"
  }

  val forgotPassword = object : NavigationCommand {

    override val arguments = emptyList<NamedNavArgument>()

    override val destination = "forgot_password"

  }
}