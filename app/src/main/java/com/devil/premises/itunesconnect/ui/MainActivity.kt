package com.devil.premises.itunesconnect.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.devil.premises.itunesconnect.R
import com.devil.premises.itunesconnect.db.ResultDatabase
import com.devil.premises.itunesconnect.repository.ITunesRepository
import com.devil.premises.itunesconnect.viewmodels.ITunesViewModel
import com.devil.premises.itunesconnect.viewmodels.ITunesViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ITunesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.mainNavHostFragment)

        val iTunesRepository = ITunesRepository(ResultDatabase(this))
        val viewModelProviderFactory = ITunesViewModelProviderFactory(application, iTunesRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ITunesViewModel::class.java)
        bottomNavigationView.setupWithNavController(navController)
    }
}