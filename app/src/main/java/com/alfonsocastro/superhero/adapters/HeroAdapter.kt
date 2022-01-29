package com.alfonsocastro.superhero.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alfonsocastro.superhero.R
import com.alfonsocastro.superhero.adapters.HeroAdapter.HeroViewHolder
import com.alfonsocastro.superhero.databinding.HeroListItemBinding
import com.alfonsocastro.superhero.model.Hero

/**
 * [ListAdapter] that implements [Hero] objects into [HeroViewHolder] using [HeroListItemBinding] ViewBinding
 */
class HeroAdapter(private val onItemClicked: (Hero) -> Unit) : ListAdapter<Hero,
        HeroAdapter.HeroViewHolder>(HeroViewHolder) {

    // Creates a LayoutInflater from parent context. Then pass it to the HeroviewHolder constructer.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        // We create a normal [HeroViewHolder] inflating the Binding
        val viewHolder = HeroViewHolder(
            HeroListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
        // Then we call the onItemClicked to pass the current Hero
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    // We get the current Hero from the ListAdapter getItem method and call the HeroViewHolder.bind() method.
    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = getItem(position)
        holder.bind(hero)
    }

    /**
     * A [RecyclerView.ViewHolder] that requires a [HeroListItemBinding].
     */
    class HeroViewHolder(private var binding: HeroListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the hero provided with the [HeroListItemBinding] [Hero].
         * Then calls to execute pending bindings method.
         */
        fun bind(hero: Hero) {
            binding.heroImage.load(hero.image.url)
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