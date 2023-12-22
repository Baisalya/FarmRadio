package com.example.farmradio.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.farmradio.R
import com.example.farmradio.dataclass.VideoItem

class VideoAdapter : ListAdapter<VideoItem, VideoAdapter.VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.list_item_video, parent, false)
        return VideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoItem = getItem(position)
        holder.bind(videoItem)
    }

    class VideoViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(videoItem: VideoItem) {
            val imageViewThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
            val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
            textViewTitle.text = videoItem.name
            Glide.with(itemView)
                .load(videoItem.uri)
                .error(R.drawable.error_image)
                .into(imageViewThumbnail)
            // Now you can use videoItem.uri to load and display video thumbnail or other details
            // You can use a library like Glide or Picasso for loading images
            // Set a click listener on the image view
            imageViewThumbnail.setOnClickListener {
                // Open the gallery or perform any other action on click
                videoItem.uri?.let { it1 -> openGallery(itemView.context, it1) }
            }

        }

        private fun openGallery(context: Context, imageUri: Uri) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(imageUri, "video/mp4")
            context.startActivity(intent)
        }
    }

    private class VideoDiffCallback : DiffUtil.ItemCallback<VideoItem>() {
        override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
            return oldItem == newItem
        }
    }
}