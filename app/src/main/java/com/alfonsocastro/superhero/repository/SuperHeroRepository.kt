package com.alfonsocastro.superhero.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alfonsocastro.superhero.api.SuperHeroApiService
import com.alfonsocastro.superhero.data.SuperHeroPagingSource
import com.alfonsocastro.superhero.model.Hero
import kotlinx.coroutines.flow.Flow

class SuperHeroRepository(private val service: SuperHeroApiService) {

    fun getSuperHeroResultStream(): Flow<PagingData<Hero>>{
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = true
        ),
            pagingSourceFactory = { SuperHeroPagingSource(service)}
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}