package com.afrosin.films.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.afrosin.films.repository.POSTER_URL
import com.bumptech.glide.Glide

fun preparePosterUrl(imageUrl: String?): String {
    return "$POSTER_URL${imageUrl}"
}

fun loadImage(
    path: String?,
    container: ImageView,
    @DrawableRes placeholder: Int = ResourcesCompat.ID_NULL
) {
    if (path != null) {
        Glide.with(container.context)
            .load(path)
            .placeholder(placeholder)
            .into(container)
    }
}