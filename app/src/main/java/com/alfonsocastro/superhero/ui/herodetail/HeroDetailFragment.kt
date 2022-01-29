package com.alfonsocastro.superhero.ui.herodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.alfonsocastro.superhero.R
import com.alfonsocastro.superhero.databinding.FragmentHeroDetailBinding

class HeroDetailFragment : Fragment() {

    private val viewModel: HeroDetailViewModel by viewModels {
        HeroDetailViewModel.HeroDetailViewModelFactory(args.heroId)
    }

    private val args: HeroDetailFragmentArgs by navArgs()

    private var _binding: FragmentHeroDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHeroDetailBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe hero on ViewModel and set texts
        viewModel.hero.observe(viewLifecycleOwner) { hero ->
            // Load hero Image to ImageView
            binding.heroImage.load(hero.image.url) {
                placeholder(R.drawable.ic_broken_image)
                error(R.drawable.ic_connection_error)
            }
            // Set Hero Name to TextView
            binding.heroName.text = hero.name
            // Set Hero PowerStats text
            binding.powerstats.text = getString(
                R.string.power_stats_format,
                hero.powerStats.intelligence,
                hero.powerStats.strength,
                hero.powerStats.speed,
                hero.powerStats.durability,
                hero.powerStats.power,
                hero.powerStats.combat
            )
            // Set Hero Biography text
            binding.bio.text = getString(
                R.string.bio_format,
                hero.biography.fullName,
                hero.biography.alterEgos,
                hero.biography.aliases.joinToString(),
                hero.biography.placeOfBirth,
                hero.biography.firstAppearance,
                hero.biography.publisher,
                hero.biography.alignment
            )
            // Set Hero Appearance text
            binding.appearance.text = getString(
                R.string.appearance_format,
                hero.appearance.gender,
                hero.appearance.race,
                hero.appearance.height.joinToString(),
                hero.appearance.weight.joinToString(),
                hero.appearance.eyeColor,
                hero.appearance.hairColor
            )
            // Set Hero Work text
            binding.work.text = getString(
                R.string.work_format,
                hero.work.occupation,
                hero.work.base
            )
            // Set Hero Connections text
            binding.connections.text = getString(
                R.string.connections_format,
                hero.connections.groupAffiliation,
                hero.connections.relatives
            )
        }
    }

}