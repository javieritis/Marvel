package com.marvel.characters.library.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object ImageLoader {
    fun loadImage(
        context: Context,
        imageUrl: String? = null,
        bitmap: Bitmap? = null,
        placeholder: Drawable? = null,
        view: ImageView? = null,
        rounded: Boolean = false,
        onCompletion: ((isSuccess: Boolean, drawable: Drawable?) -> Unit)? = null
    ) {

        if (onCompletion != null) {
            view?.setImageDrawable(placeholder)
        }

        var builder = Glide.with(context)
            .load(imageUrl ?: bitmap)
            .placeholder(placeholder)

        if (rounded) {
            builder = builder.apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.circleCropTransform())
        }

        when {
            view != null -> builder.into(view)
            onCompletion != null -> builder.into(buildTarget(onCompletion))
        }
    }

    private fun <T> buildTarget(onCompletion: (isSuccess: Boolean, model: T?) -> Unit): CustomTarget<T> {
        return object : CustomTarget<T>() {
            override fun onResourceReady(resource: T, p1: Transition<in T>?) {
                onCompletion(true, resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                onCompletion(false, null)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        }
    }
}