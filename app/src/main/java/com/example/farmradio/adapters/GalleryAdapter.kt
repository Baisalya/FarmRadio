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
import com.example.farmradio.dataclass.Data
import com.example.farmradio.dataclass.ImageItem

class GalleryAdapter : ListAdapter<ImageItem, GalleryAdapter.ImageViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.list_item_video, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bind(imageItem)
    }

    class ImageViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(imageItem: ImageItem) {
            val imageViewThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
            val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
            textViewTitle.text = imageItem.name
            Glide.with(itemView)
                .load(imageItem.uri)
                .error(R.drawable.error_image)
                .into(imageViewThumbnail)

            // Set a click listener on the image view
            imageViewThumbnail.setOnClickListener {
                // Perform any action specific to images, such as opening a full-screen image viewer
                imageItem.uri?.let { it1 -> openImageViewer(itemView.context, it1) }
            }
        }

        private fun openImageViewer(context: Context, imageUri: Uri) {
            // Implement logic to open a full-screen image viewer activity or fragment
            // You can use an Intent or a dedicated image viewer library for this purpose
            // For simplicity, let's assume you have an intent to open an image viewer
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(imageUri, "image/*")
            context.startActivity(intent)
        }
    }

    private class ImageDiffCallback : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }
    }
}
