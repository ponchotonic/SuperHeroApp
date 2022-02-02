package com.alfonsocastro.superhero.ui.herolist

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alfonsocastro.superhero.R
import com.alfonsocastro.superhero.api.SuperHeroApi
import com.alfonsocastro.superhero.data.SuperHeroPagingSource
import com.alfonsocastro.superhero.model.Hero
import com.alfonsocastro.superhero.repository.SuperHeroRepository
import com.alfonsocastro.superhero.ui.herodetail.HeroDetailViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

enum class SuperHeroApiStatus { LOADING, ERROR, DONE }

class HeroListViewModel(private val repository: SuperHeroRepository) : ViewModel() {

    // Paging
    private var currentQueryValue: String? = null
    private lateinit var currentSearchResult: Flow<PagingData<Hero>>

    private val _status = MutableLiveData<SuperHeroApiStatus>()
    val status: LiveData<SuperHeroApiStatus> = _status

    private val _heroes = MutableLiveData<List<Hero>>()
    val heroes: LiveData<List<Hero>> = _heroes


    /**
     * Call getNextSuperHeroes() on init so we can display status immediately.
     */
    init {
        //Log.d("HeroListViewModel", "Init called. ${currentSearchResult.toString()}")
        getNextSuperHeroes()
    }

    /**
     *
     */
    fun getNextSuperHeroes(): Flow<PagingData<Hero>> {
        val newResult: Flow<PagingData<Hero>> = repository.getSuperHeroResultStream()
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    /**
     * Gets Heroes information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getSuperHeroes() {
        viewModelScope.launch {
            try {
                _status.value = SuperHeroApiStatus.DONE
                _heroes.value = listOf(
                    SuperHeroApi.retrofitService.getHero(1),
                    SuperHeroApi.retrofitService.getHero(2),
                    SuperHeroApi.retrofitService.getHero(3),
                    SuperHeroApi.retrofitService.getHero(4),
                    SuperHeroApi.retrofitService.getHero(5),
                    SuperHeroApi.retrofitService.getHero(6),
                    SuperHeroApi.retrofitService.getHero(7),
                    SuperHeroApi.retrofitService.getHero(8),
                    SuperHeroApi.retrofitService.getHero(9),
                    SuperHeroApi.retrofitService.getHero(10),
                )
                Log.d("HeroListViewModel", "Loaded ${_heroes.value?.size} heroes")
            } catch (e: Exception) {
                _status.value = SuperHeroApiStatus.ERROR
                _heroes.value = listOf()
                Log.d("HeroListViewModel", "Error", e)
            }
        }
    }

    class HeroListViewModelFactory(private val repository: SuperHeroRepository):
            ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HeroListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HeroListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}