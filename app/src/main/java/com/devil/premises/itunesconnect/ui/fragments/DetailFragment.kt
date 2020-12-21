package com.devil.premises.itunesconnect.ui.fragments

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.devil.premises.itunesconnect.R
import com.devil.premises.itunesconnect.viewmodels.ITunesViewModel
import com.devil.premises.itunesconnect.ui.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class DetailFragment : Fragment(R.layout.fragment_detail) {

    lateinit var viewModel: ITunesViewModel
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var play: MaterialButton

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val imgTrack = view.findViewById<ImageView>(R.id.img_Track)
        val trackName = view.findViewById<TextView>(R.id.txt_Track)
        val artistName = view.findViewById<TextView>(R.id.txt_ArtistName)
        val collectionName = view.findViewById<TextView>(R.id.txt_CollectionName)
        val genreName = view.findViewById<TextView>(R.id.txt_GenreName)
        val releaseDate = view.findViewById<TextView>(R.id.txt_ReleaseDate)
        play = view.findViewById<MaterialButton>(R.id.btn_Play)
        play.visibility = View.INVISIBLE
        val fab = view.findViewById<FloatingActionButton>(R.id.fabFavourite)

        val args = arguments?.let { DetailFragmentArgs.fromBundle(it) }
        val result = args?.result
        trackName.text = result?.trackName
        artistName.text = result?.artistName
        collectionName.text = result?.collectionName
        releaseDate.text = result?.releaseDate
        genreName.text = result?.primaryGenreName

        if(viewModel.hasInternetConnection()){
            play.visibility = View.VISIBLE
        }

        if(result?.isStreamable == false){
            play.visibility = View.INVISIBLE
        }

        Glide.with(this).load(result?.artworkUrl100).into(imgTrack)

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                    AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            )
            setDataSource(result?.previewUrl)
        }

        fab.setOnClickListener{
            if (result != null) {
                viewModel.saveResult(result)
                Snackbar.make(view, "Saved Successfully",Snackbar.LENGTH_SHORT).show()
            }
        }

        play.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                play.text = "Play Demo"
                mediaPlayer.stop()
            } else {
                play.text = "Stop Demo"

                mediaPlayer.prepare()
                mediaPlayer.start()
            }
        }
//        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                if(mediaPlayer!!.isPlaying){
//                    mediaPlayer.stop()
//                }
//            }
//        })
    }

    override fun onPause() {
        if (mediaPlayer.isPlaying)
            mediaPlayer.stop()
            play.text = "Play Demo"
        super.onPause()
    }
}