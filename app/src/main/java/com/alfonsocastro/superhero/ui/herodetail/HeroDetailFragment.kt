package com.alfonsocastro.superhero.ui.herodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alfonsocastro.superhero.databinding.FragmentHeroDetailBinding

class HeroDetailFragment: Fragment() {

    private val viewModel: HeroDetailViewModel by viewModels {
        HeroDetailViewModel.HeroDetailViewModelFactory(args.heroId)
    }

    private val args: HeroDetailFragmentArgs by navArgs()

    private var _binding: FragmentHeroDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHeroDetailBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        return binding.root
    }

}