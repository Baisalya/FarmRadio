package com.example.farmradio.dataclass

import android.net.Uri

/*
class Video {
    var name: String? = null
    var uri: Uri? = null

    fun setName(name: String) {
        this.name = name
    }

    fun setUri(uri: Uri) {
        this.uri = uri
    }

    fun getName(): String? {
        return name
    }

    fun getUri(): Uri? {
        return uri
    }
}
*/
/*
data class VideoItem(private var _uri: Uri, private var _title: String) {
    var uri: Uri
        get() = _uri
        set(value) {
            _uri = value
        }

    var title: String
        get() = _title
        set(value) {
            _title = value
        }
}
*/
data class VideoItem(
    var uri: Uri? = null,
    var name: String? = null
)
data class Data(
    val name: String,
    val uri: Uri,
    // Add any other properties you need, like thumbnailUrl, etc.
)

/*
class Data(
    val title: String,
    val videoUrl: String,
    val imageUrl: String,
    val audioUrl: String
)*/
data class Video(
    val uri: Uri,  // Assuming you have imported android.net.Uri
    val name: String
)
