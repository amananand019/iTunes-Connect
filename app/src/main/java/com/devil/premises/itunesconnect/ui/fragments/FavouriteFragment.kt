package com.devil.premises.itunesconnect.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devil.premises.itunesconnect.R
import com.devil.premises.itunesconnect.ui.ITunesViewModel
import com.devil.premises.itunesconnect.ui.MainActivity

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    lateinit var viewModel: ITunesViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }
}