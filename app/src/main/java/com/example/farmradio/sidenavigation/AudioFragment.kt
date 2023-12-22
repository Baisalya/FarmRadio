package com.example.farmradio.sidenavigation

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmradio.R
import com.example.farmradio.adapters.AudioAdapter
import com.example.farmradio.adapters.VideoAdapter
import com.example.farmradio.databinding.FragmentVideosBinding
import com.example.farmradio.dataclass.AudioItem
import com.example.farmradio.dataclass.VideoItem
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import java.util.concurrent.Executors

class AudioFragment : Fragment() {
    private lateinit var binding: FragmentVideosBinding
    private lateinit var audioAdapter: AudioAdapter

    private val executorService = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentVideosBinding.inflate(layoutInflater)
        audioAdapter = AudioAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_audio, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recViewAudio)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = audioAdapter
        loadVideoItems()
        return view
    }

    override fun onStop() {
        super.onStop()
        stopAudioPlayback()
    }

    private fun stopAudioPlayback() {
        audioAdapter.stopAudio()
    }

    private fun loadVideoItems() {
        val directory = "Audio"
        val reference = FirebaseStorage.getInstance().getReference().child(directory)

        reference.listAll()
            .addOnSuccessListener { result ->
                val videoItems = mutableListOf<AudioItem>()

                val downloadTasks = mutableListOf<Task<Uri>>() // Keep track of download tasks

                result.items.forEach { storageReference ->
                    val video = AudioItem()

                    video.name = storageReference.name
                    val downloadTask = storageReference.downloadUrl
                        .addOnSuccessListener { uri ->
                            video.uri = uri
                        }
                        .addOnFailureListener {
                            // Handle failures in getting download URL
                            // TODO: Add error handling logic
                        }

                    downloadTasks.add(downloadTask)
                    videoItems.add(video)
                }

                // Wait for all download tasks to complete before updating the adapter
                Tasks.whenAllComplete(downloadTasks)
                    .addOnSuccessListener {
                        // All download tasks completed successfully
                        audioAdapter.submitList(videoItems)

                        if (videoItems.isNotEmpty()) {
                            val firstVideoUri = videoItems[0].uri
                            showToast("Video Uri: $firstVideoUri")
                        }
                    }
                    .addOnFailureListener {
                        // Handle failures in downloading URLs
                        // TODO: Add error handling logic
                    }
            }
            .addOnFailureListener {
                // Handle failures in listing items
                // TODO: Add error handling logic
            }
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }
}
