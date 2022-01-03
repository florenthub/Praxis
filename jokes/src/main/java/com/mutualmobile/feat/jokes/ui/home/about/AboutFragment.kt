package com.mutualmobile.feat.jokes.ui.home.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import com.mutualmobile.feat.jokes.R
import com.mutualmobile.feat.jokes.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint
import com.mutualmobile.feat.jokes.BR

@AndroidEntryPoint
class AboutFragment : DialogFragment(), LifecycleOwner {

  companion object {
    fun newInstance(): AboutFragment {
      val fragment = AboutFragment()
      val args = Bundle()
      fragment.arguments = args
      return fragment
    }
  }

  private lateinit var binding: FragmentAboutBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
    binding.mutualMobileWebLink.movementMethod = LinkMovementMethod.getInstance()
    return binding.root
  }

}