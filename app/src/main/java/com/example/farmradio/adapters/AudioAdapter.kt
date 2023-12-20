package com.example.farmradio.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.farmradio.dataclass.Data

class AudioAdapter(val videoList: List<Data>) :
    RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {
    inner class AudioViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}