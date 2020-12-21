package com.devil.premises.itunesconnect.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.devil.premises.itunesconnect.R
import com.devil.premises.itunesconnect.adapters.ITunesAdapter
import com.devil.premises.itunesconnect.viewmodels.ITunesViewModel
import com.devil.premises.itunesconnect.ui.MainActivity
import com.google.android.material.snackbar.Snackbar

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    private lateinit var viewModel: ITunesViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var iTunesAdapter: ITunesAdapter
    private lateinit var rvFavourite: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        rvFavourite = view.findViewById(R.id.rvFavourite)

        setupRecyclerView()

        iTunesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("result",it)
            }
            findNavController().navigate(R.id.action_favouriteFragment2_to_detailFragment, bundle)

//            val action = SearchFragmentDirections.actionSearchFragment2ToDetailFragment(it)
//            findNavController().navigate(action)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val result = iTunesAdapter.differ.currentList[position]
                viewModel.deleteResults(result)
                Snackbar.make(view, "Delete Successful",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveResult(result)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvFavourite)
        }
        viewModel.getSavedITunes().observe(viewLifecycleOwner, Observer {results ->
            iTunesAdapter.differ.submitList(results)
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
        rvFavourite.apply {
            adapter = iTunesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}