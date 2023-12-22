package com.example.farmradio.sidenavigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.farmradio.R
import com.example.farmradio.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentHomeBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayout = view.findViewById<LinearLayout>(R.id.share)
        linearLayout.setOnClickListener {
            navigateToWhatsApp()
        }
        val whatsappButton = view.findViewById<ImageButton>(R.id.whatsappButton)
        whatsappButton.setOnClickListener {
            navigateToWhatsApp()
        }

        // Set background image from URL using Picasso
        val backgroundImageView = view.findViewById<ImageView>(R.id.backgroundImageView)
        val imageUrl = "https://lh3.googleusercontent.com/p/AF1QipNHT5rw2y1-xZNx-pRJIEAJzkO4l7j3JgnuGS6C=s1360-w1360-h1020" // Replace with your image URL
        Picasso.get().load(imageUrl).fit().centerCrop().into(backgroundImageView)
    }

    fun navigateToWhatsApp() {
        val whatsappUrl = "https://api.whatsapp.com/send?phone=+918249166181" // Replace with the phone number
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl))
        startActivity(intent)
    }
}

