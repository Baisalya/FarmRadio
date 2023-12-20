package com.example.farmradio.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.farmradio.dataclass.Data

class GalleryAdapter(val galleryList: List<Data>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    inner class GalleryViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}