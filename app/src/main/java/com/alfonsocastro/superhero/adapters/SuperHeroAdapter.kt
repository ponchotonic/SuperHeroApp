package com.alfonsocastro.superhero.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alfonsocastro.superhero.R
import com.alfonsocastro.superhero.databinding.HeroListItemBinding
import com.alfonsocastro.superhero.model.Hero

/**
 * SuperHero adapter that works with Paging Library
 */
class SuperHeroAdapter(private val onItemClicked: (Hero) -> Unit) : PagingDataAdapter<Hero, SuperHeroAdapter.SuperHeroViewHolder>(SuperHeroViewHolder.DiffCallback){

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        val hero = getItem(position)
        hero?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        // We create a normal [HeroViewHolder] inflating the Binding
        val viewHolder = SuperHeroViewHolder(
            HeroListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
        // Then we call the onItemClicked to pass the current Hero
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            getItem(position)?.let {
                    hero -> onItemClicked(hero)
            }
        }
        return viewHolder
    }

    /**
     * A [RecyclerView.ViewHolder] that requires a [HeroListItemBinding].
     */
    class SuperHeroViewHolder(private var binding: HeroListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the hero provided with the [HeroListItemBinding] [Hero].
         * Then calls to execute pending bindings method.
         */
        fun bind(hero: Hero) {
            binding.heroImage.load(hero.image.url) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            binding.heroName.text = hero.name
            binding.heroId.text = binding.root.context.getString(R.string.id_format, hero.id)
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Hero>() {
            override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
                return oldItem == newItem
            }

        }
    }
}