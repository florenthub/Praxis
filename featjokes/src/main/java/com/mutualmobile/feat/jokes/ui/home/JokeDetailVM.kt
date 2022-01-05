package com.mutualmobile.feat.jokes.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mutualmobile.feat.jokes.ui.model.UIJoke
import com.mutualmobile.praxis.navigator.Navigator
import com.mutualmobile.praxis.navigator.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class JokeDetailVM @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val navigator: Navigator
) : ViewModel() {
  private val jokeId = savedStateHandle.get<Long>(Screen.JokeDetail.navArguments.first().name)!!

  var uiState = MutableStateFlow<UiState>(UiState.LoadingState)
    private set

  init {
    val joke = InMemoryDataTemp.jokes?.find { it.jokeId == jokeId.toInt() }
    joke?.let {
      uiState.value = UiState.SuccessState(joke)
    }
  }

  sealed class UiState {
    object LoadingState : UiState()
    data class SuccessState(
      val joke: UIJoke,
    ) : UiState()

    object ErrorState : UiState()
  }

}