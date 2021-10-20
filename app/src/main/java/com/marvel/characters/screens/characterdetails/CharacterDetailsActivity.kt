package com.marvel.characters.screens.characterdetails

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearSnapHelper
import com.marvel.characters.R
import com.marvel.characters.data.base.MediaItem
import com.marvel.characters.data.character.Character
import com.marvel.characters.data.comic.Comic
import com.marvel.characters.data.serie.Serie
import com.marvel.characters.databinding.ActivityCharacterDetailsBinding
import com.marvel.characters.library.base.BaseActivity
import com.marvel.characters.library.image.ImageLoader
import com.marvel.characters.screens.characterdetails.comic.ComicAdapter
import com.marvel.characters.screens.characterdetails.serie.SerieAdapter
import com.marvel.characters.screens.comic.MediaDetailsDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
@AndroidEntryPoint
class CharacterDetailsActivity : BaseActivity<ActivityCharacterDetailsBinding>() {

    companion object {
        const val EXTRA_CHARACTER = "character"
        const val EXTRA_IMAGE_TRANSITION_NAME = "image_transition_name"
    }

    override val binding by viewBinding(ActivityCharacterDetailsBinding::inflate)
    private val viewModel: CharacterDetailsViewModel by viewModels()

    private val comicAdapter by lazy {
        ComicAdapter(emptyList()) { comic ->
            openMediaDetailsDialog(comic)
        }
    }
    private val seriesAdapter by lazy {
        SerieAdapter(emptyList()) { serie ->
            openMediaDetailsDialog(serie)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.character.observe(this) {
            setUpViews(it)
        }
        viewModel.comics.observe(this) {
            setUpComics(it)
        }
        viewModel.series.observe(this) {
            setUpSeries(it)
        }

        binding.rvComics.adapter = comicAdapter
        LinearSnapHelper().attachToRecyclerView(binding.rvComics)

        binding.rvSeries.adapter = seriesAdapter
        LinearSnapHelper().attachToRecyclerView(binding.rvSeries)

        binding.imageView.setOnClickListener {
            viewModel.openImageDetails()
        }

        binding.swipeRefresh.setColorSchemeResources(R.color.red)
        binding.swipeRefresh.setOnRefreshListener {
            uiScope.launch {
                viewModel.fetchCharacter()
                binding.swipeRefresh.isRefreshing = false
            }
        }

        intent.extras?.let { it ->
            it.getParcelable<Character>(EXTRA_CHARACTER)?.let { character ->
                viewModel.character.value = character
            }

            it.getString(EXTRA_IMAGE_TRANSITION_NAME)?.let { transitionName ->
                binding.imageView.transitionName = transitionName
            }
        }
    }

    private fun setUpViews(character: Character) {
        supportActionBar?.title = character.name
        binding.textViewDescription.text = character.description

        ImageLoader.loadImage(
            context = this,
            imageUrl = character.thumbnail.imageUrl,
            view = binding.imageView
        )

        if (character.hasComics) {
            binding.containerComics.isVisible = true
            binding.loadingViewComics.root.isVisible = true
            viewModel.fetchComics()
        } else {
            binding.containerComics.isVisible = false
        }

        if (character.hasSeries) {
            binding.containerSeries.isVisible = true
            binding.loadingViewSeries.root.isVisible = true
            viewModel.fetchSeries()
        } else {
            binding.containerSeries.isVisible = false
        }
    }

    private fun setUpComics(comics: List<Comic>?) {
        binding.loadingViewComics.root.isVisible = false
        comics?.let {
            binding.rvComics.isVisible = true
            comicAdapter.setComics(it)
        }
    }

    private fun setUpSeries(series: List<Serie>?) {
        binding.loadingViewSeries.root.isVisible = false
        series?.let {
            binding.rvSeries.isVisible = true
            seriesAdapter.setSeries(it)
        }
    }

    private fun openMediaDetailsDialog(media: MediaItem) {
        val dialog = MediaDetailsDialog(media)
        dialog.show(supportFragmentManager, MediaDetailsDialog.TAG)
    }
}