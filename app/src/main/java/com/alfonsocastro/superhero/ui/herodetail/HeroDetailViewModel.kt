package com.alfonsocastro.superhero.ui.herodetail

import androidx.lifecycle.*
import com.alfonsocastro.superhero.api.SuperHeroApi
import com.alfonsocastro.superhero.model.Hero
import kotlinx.coroutines.launch

class HeroDetailViewModel(heroId: String) : ViewModel() {

    private val _hero = MutableLiveData<Hero>()
    val hero: LiveData<Hero> = _hero

    init {
        getHero(heroId)
    }

    /**
     * Get [Hero] by ID from DataSource and sets to the Hero ViewModel
     */
    private fun getHero(heroId: String) {
        viewModelScope.launch {
            try {
                _hero.value = SuperHeroApi.retrofitService.getHero(heroId.toInt())

            } catch (e: Exception) {

            }
        }
    }

    class HeroDetailViewModelFactory(private val heroId: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HeroDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HeroDetailViewModel(heroId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}