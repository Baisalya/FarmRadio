package com.example.farmradio.adapters

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
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
import com.example.farmradio.dataclass.AudioItem

class AudioAdapter : ListAdapter<AudioItem, AudioAdapter.AudioViewHolder>(AudioDiffCallback()) {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying: Boolean = false
    private var currentlyPlayingPosition: Int = RecyclerView.NO_POSITION
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.list_item_audio, parent, false)
        return AudioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val audioItem = getItem(position)
        holder.bind(audioItem)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        stopAudio()
    }

    fun stopAudio() {
        if (isPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            isPlaying = false
            currentlyPlayingPosition = RecyclerView.NO_POSITION
            notifyDataSetChanged()
        }
    }

    inner class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            // Set a click listener on the image view
            itemView.findViewById<ImageView>(R.id.imageViewThumbnail).setOnClickListener {
                val position = bindingAdapterPosition
                if (isPlaying) {
                    pauseAudio(position)
                } else {
                    getItem(position)?.uri?.let { it1 -> playAudio(itemView.context, it1, position) }
                }
            }
        }

        fun bind(audioItem: AudioItem) {
            val imageViewThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
            val imageViewPlayPause: ImageView = itemView.findViewById(R.id.imageViewPlayPause)
            val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)

            textViewTitle.text = audioItem.name
            Glide.with(itemView)
                .load(audioItem.uri)
                .error(R.drawable.musicbg)
                .into(imageViewThumbnail)

            // Update the play/pause icon based on the isPlaying state
            if (bindingAdapterPosition == currentlyPlayingPosition && isPlaying) {
                imageViewPlayPause.setImageResource(R.drawable.ic_pause)
            } else {
                imageViewPlayPause.setImageResource(R.drawable.ic_play)
            }
        }
    }

    private fun playAudio(context: Context, audioUri: Uri, position: Int) {
        stopAudio() // Stop any currently playing audio before starting a new one

        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(context, audioUri)
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            mediaPlayer?.prepare()
            mediaPlayer?.start()
            isPlaying = true
            currentlyPlayingPosition = position

            // Update the play/pause icon after starting the audio
            handler.post {
                notifyItemChanged(position)
            }

            mediaPlayer?.setOnCompletionListener {
                // Audio playback completed, stop and reset
                stopAudio()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun pauseAudio(position: Int) {
        mediaPlayer?.pause()
        isPlaying = false

        // Update the play/pause icon after pausing the audio
        currentlyPlayingPosition = RecyclerView.NO_POSITION
        handler.post {
            notifyItemChanged(position)
        }
    }
}

class AudioDiffCallback : DiffUtil.ItemCallback<AudioItem>() {
    override fun areItemsTheSame(oldItem: AudioItem, newItem: AudioItem): Boolean {
        // Assuming each item has a unique identifier, you should replace 'id' with your actual identifier
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: AudioItem, newItem: AudioItem): Boolean {
        // Compare all properties of AudioItem here
        return oldItem == newItem
    }
}
