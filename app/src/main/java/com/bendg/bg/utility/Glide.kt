package com.bendg.bg.utility

import android.widget.ImageView
import com.bendg.bg.R
import com.bumptech.glide.Glide

class Glide {

    fun setImage(url: String, image: ImageView) {
        Glide
            .with(image.context)
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .centerCrop()
            .into(image)
    }
}