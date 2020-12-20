package com.devil.premises.itunesconnect.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devil.premises.itunesconnect.repository.ITunesRepository

class ITunesViewModelProviderFactory(val itunesRepository: ITunesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ITunesViewModel(itunesRepository) as T
    }
}