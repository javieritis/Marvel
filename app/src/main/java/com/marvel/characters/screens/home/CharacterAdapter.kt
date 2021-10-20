package com.marvel.characters.screens.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvel.characters.databinding.CharacterItemBinding

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.marvel.characters.R
import com.marvel.characters.data.character.Character
import com.marvel.characters.library.image.ImageLoader

class CharacterAdapter(private val characterClickListener: (character: Character, imageView: ImageView) -> Unit) :
    PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(DiffUtilCallBack) {

    class CharacterViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            character: Character,
            characterClickListener: (character: Character, imageView: ImageView) -> Unit
        ) {
            binding.card.setOnClickListener {
                characterClickListener.invoke(character, binding.image)
            }
            binding.title.text = character.name

            ImageLoader.loadImage(
                context = binding.root.context,
                imageUrl = "${character.thumbnail.imageUrl}",
                placeholder = binding.root.context.getDrawable(R.drawable.profile_placeholder),
                view = binding.image
            )
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, characterClickListener)
            setAnimation(holder.itemView, position, holder.itemView.context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemBinding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(itemBinding)
    }

    private var lastPosition = -1
    private fun setAnimation(viewToAnimate: View, position: Int, context: Context) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animation_alpha)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}