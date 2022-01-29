package com.alfonsocastro.superhero.ui.herolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfonsocastro.superhero.api.SuperHeroApi
import com.alfonsocastro.superhero.model.Hero
import kotlinx.coroutines.launch

enum class SuperHeroApiStatus { LOADING, ERROR, DONE }

class HeroListViewModel : ViewModel() {

    private val _status = MutableLiveData<SuperHeroApiStatus>()
    val status: LiveData<SuperHeroApiStatus> = _status

    private val _heroes = MutableLiveData<List<Hero>>()
    val heroes: LiveData<List<Hero>> = _heroes

    /**
     * Call getHeroes() on init so we can display status immediately.
     */
    init {
        getSuperHeroes()
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
}