package com.alfonsocastro.superhero.ui.herolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfonsocastro.superhero.adapters.HeroAdapter
import com.alfonsocastro.superhero.databinding.FragmentHeroListBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HeroListFragment: Fragment() {

    private val viewModel: HeroListViewModel by viewModels()

    private var _binding: FragmentHeroListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHeroListBinding.inflate(inflater, container, false)

        binding.heroListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.heroListRecyclerView.adapter = HeroAdapter {
            val action = HeroListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(
                heroId = it.id
            )
            findNavController().navigate(action)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.heroes.observe(viewLifecycleOwner) { heroList ->
            (binding.heroListRecyclerView.adapter as HeroAdapter).submitList(heroList)
        }
    }

}