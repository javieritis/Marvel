package com.marvel.characters.screens.comic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marvel.characters.R
import com.marvel.characters.data.base.MediaItem
import com.marvel.characters.data.comic.Comic
import com.marvel.characters.data.serie.Serie
import com.marvel.characters.databinding.DialogMediaDetailsBinding
import com.marvel.characters.library.ActivityNavigator
import com.marvel.characters.library.image.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject


@ExperimentalSerializationApi
@AndroidEntryPoint
class MediaDetailsDialog(
    private val mediaItem: MediaItem
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "MediaDetailsDialog"
    }

    private lateinit var binding: DialogMediaDetailsBinding

    @Inject
    lateinit var activityNavigator: ActivityNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMediaDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ImageLoader.loadImage(
            requireContext(),
            imageUrl = mediaItem.thumbnail?.imageUrl,
            view = binding.imageView
        )

        binding.imageView.setOnClickListener {
            mediaItem.thumbnail?.let {
                activityNavigator.openImageDetails(it.imageUrl)
            }
        }

        binding.title.text = mediaItem.title
        binding.description.text = mediaItem.description

        when (mediaItem) {
            is Comic -> setUpComicFields(mediaItem)
            is Serie -> setUpSerieFields(mediaItem)
        }
    }

    private fun setUpComicFields(comic: Comic) {
        if (comic.isbn.isNullOrEmpty()) {
            binding.isbn.isVisible = false
        } else {
            binding.isbn.isVisible = true
            binding.isbn.text = getString(R.string.isbn, comic.isbn)
        }

        if (comic.pageCount == null || comic.pageCount == 0) {
            binding.pageCount.isVisible = false
        } else {
            binding.pageCount.isVisible = true
            binding.pageCount.text = getString(R.string.page_count, comic.pageCount)
        }
    }

    private fun setUpSerieFields(serie: Serie) {
        if (serie.rating.isNullOrEmpty()) {
            binding.rating.isVisible = false
        } else {
            binding.rating.isVisible = true
            binding.rating.text = getString(R.string.rating, serie.rating)
        }
    }
}