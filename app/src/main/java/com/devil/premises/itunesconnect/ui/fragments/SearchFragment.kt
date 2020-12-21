package com.devil.premises.itunesconnect.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devil.premises.itunesconnect.R
import com.devil.premises.itunesconnect.adapters.ITunesAdapter
import com.devil.premises.itunesconnect.viewmodels.ITunesViewModel
import com.devil.premises.itunesconnect.ui.MainActivity
import com.devil.premises.itunesconnect.util.Resource
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val TAG = "SearchITuneFragment"

    private lateinit var viewModel: ITunesViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var iTunesAdapter: ITunesAdapter
    private lateinit var rvSearch: RecyclerView
    private lateinit var message: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        progressBar = view.findViewById(R.id.searchProgressBar)
        val searchText = view.findViewById<TextInputEditText>(R.id.etSearch)
        rvSearch = view.findViewById(R.id.rvSearch)
        message = view.findViewById(R.id.txt_message)
        setupRecyclerView()

        var job: Job? = null
        searchText.addTextChangedListener{ editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                        viewModel.searchITunes(editable.toString())
                }
            }

        }

        viewModel.searchITunes.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    message.visibility = View.INVISIBLE
                    hideProgressBar()
                    response.data?.let { iTuneResponse ->
                        iTunesAdapter.differ.submitList(iTuneResponse.results)
                    }

                    if(response.data?.resultCount  == 0){
                        message.text = "No result found"
                        message.visibility = View.VISIBLE
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(){
        iTunesAdapter = ITunesAdapter()
        rvSearch.apply {
            adapter = iTunesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}