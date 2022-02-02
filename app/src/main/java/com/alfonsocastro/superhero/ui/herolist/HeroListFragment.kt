package com.alfonsocastro.superhero.ui.herolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alfonsocastro.superhero.adapters.HeroAdapter
import com.alfonsocastro.superhero.adapters.SuperHeroAdapter
import com.alfonsocastro.superhero.api.SuperHeroApi
import com.alfonsocastro.superhero.databinding.FragmentHeroListBinding
import com.alfonsocastro.superhero.repository.SuperHeroRepository
import com.alfonsocastro.superhero.ui.herodetail.HeroDetailViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "HeroListFragment"

class HeroListFragment : Fragment() {

    private val viewModel: HeroListViewModel by viewModels {
        HeroListViewModel.HeroListViewModelFactory(SuperHeroRepository(SuperHeroApi.retrofitService))
    }

    private var _binding: FragmentHeroListBinding? = null

    // Job for paging library
    private var searchJob: Job? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = SuperHeroAdapter() {
        SuperHeroAdapter {
            val action = HeroListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(
                heroId = it.id
            )
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHeroListBinding.inflate(inflater, container, false)

        // Bind adapter to PageListAdapter
        binding.gridRecyclerView.adapter = SuperHeroAdapter {
            val action = HeroListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(
                heroId = it.id
            )
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*viewModel.heroes.observe(viewLifecycleOwner) { heroList ->
            (binding.gridRecyclerView.adapter as HeroAdapter).submitList(heroList)
        }*/

        // Call Paging from the Fragment
        loadHeroes()

    }

    private fun loadHeroes() {
        // Make sure we cancel the previous job before creating a new one
        Log.d(TAG, "loadHeroes() called")
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            Log.d(TAG, "Loading from searchJob")
            viewModel.getNextSuperHeroes().collectLatest {data ->
                Log.d(TAG, "Calling from the Flow. Data: ${data}")
                (binding.gridRecyclerView.adapter as SuperHeroAdapter).submitData(data)
            }
        }
    }

}