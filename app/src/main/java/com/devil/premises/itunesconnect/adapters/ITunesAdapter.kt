package com.devil.premises.itunesconnect.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devil.premises.itunesconnect.R
import com.devil.premises.itunesconnect.models.Result

class ITunesAdapter: RecyclerView.Adapter<ITunesAdapter.ResultViewHolder>() {

    inner class ResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivTrack = itemView.findViewById<ImageView>(R.id.img_Track)
        val tvTrackName = itemView.findViewById<TextView>(R.id.txt_TrackName)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.previewUrl == newItem.previewUrl
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_tile_preview,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(result.artworkUrl100).into(holder.ivTrack)
            holder.tvTrackName.text = result.trackName

            setOnClickListener {
                onItemClickListener?.let { it(result) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Result)-> Unit)? = null

    fun setOnItemClickListener(listener: (Result) -> Unit){
        onItemClickListener = listener
    }
}