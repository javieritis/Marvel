package com.marvel.characters.screens.characterdetails.comic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvel.characters.R
import com.marvel.characters.data.comic.Comic
import com.marvel.characters.databinding.MediaItemBinding
import com.marvel.characters.library.image.ImageLoader

class ComicAdapter(
    private var comics: List<Comic>,
    private val comicSelectedCallback: (Comic) -> Unit
) :
    RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    class ComicViewHolder(private val binding: MediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comic: Comic, comicSelectedCallback: (Comic) -> Unit) {
            binding.root.setOnClickListener {
                comicSelectedCallback.invoke(comic)
            }
            ImageLoader.loadImage(
                binding.root.context,
                imageUrl = comic.thumbnail?.imageUrl,
                view = binding.imageView,
                placeholder = binding.root.context.getDrawable(R.drawable.profile_placeholder)
            )
            binding.textView.text = comic.title
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ComicViewHolder {
        val binding =
            MediaItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ComicViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ComicViewHolder, position: Int) {
        viewHolder.bind(comics[position], comicSelectedCallback)
    }

    override fun getItemCount() = comics.size

    fun setComics(comics: List<Comic>) {
        this.comics = comics
        notifyDataSetChanged()
    }
}