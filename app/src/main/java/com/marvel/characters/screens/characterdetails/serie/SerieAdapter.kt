package com.marvel.characters.screens.characterdetails.serie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvel.characters.R
import com.marvel.characters.data.serie.Serie
import com.marvel.characters.databinding.MediaItemBinding
import com.marvel.characters.library.image.ImageLoader

class SerieAdapter(
    private var series: List<Serie>,
    private val serieSelectedCallback: (Serie) -> Unit
) :
    RecyclerView.Adapter<SerieAdapter.SerieViewHolder>() {

    class SerieViewHolder(private val binding: MediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(serie: Serie, serieSelectedCallback: (Serie) -> Unit) {
            binding.root.setOnClickListener {
                serieSelectedCallback.invoke(serie)
            }
            ImageLoader.loadImage(
                binding.root.context,
                imageUrl = serie.thumbnail?.imageUrl,
                view = binding.imageView,
                placeholder = binding.root.context.getDrawable(R.drawable.profile_placeholder)
            )
            binding.textView.text = serie.title
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SerieViewHolder {
        val binding =
            MediaItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return SerieViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SerieViewHolder, position: Int) {
        viewHolder.bind(series[position], serieSelectedCallback)
    }

    override fun getItemCount() = series.size

    fun setSeries(series: List<Serie>) {
        this.series = series
        notifyDataSetChanged()
    }
}