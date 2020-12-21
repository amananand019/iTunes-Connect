package com.devil.premises.itunesconnect.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devil.premises.itunesconnect.repository.ITunesRepository

class ITunesViewModelProviderFactory(val app: Application, val itunesRepository: ITunesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ITunesViewModel(app, itunesRepository) as T
    }
}