package com.marvel.characters.screens.image

import android.os.Bundle
import android.view.MotionEvent
import com.aghajari.zoomhelper.ZoomHelper
import com.marvel.characters.databinding.ActivityImageDetailsBinding
import com.marvel.characters.library.base.BaseActivity
import com.marvel.characters.library.image.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class ImageDetailsActivity : BaseActivity<ActivityImageDetailsBinding>() {
    companion object {
        const val EXTRA_IMAGE_URL = "image"
    }

    override val binding by viewBinding(ActivityImageDetailsBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.extras?.let {
            val urlImage = it.getString(EXTRA_IMAGE_URL)
            ImageLoader.loadImage(this, imageUrl = urlImage, view = binding.imageView)
        }
    }

    //https://github.com/Aghajari/ZoomHelper#usage-kotlin
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return ZoomHelper.getInstance().dispatchTouchEvent(ev!!,this) || super.dispatchTouchEvent(ev)
    }
}