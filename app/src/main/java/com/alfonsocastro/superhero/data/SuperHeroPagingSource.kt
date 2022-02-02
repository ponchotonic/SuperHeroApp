package com.alfonsocastro.superhero.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alfonsocastro.superhero.api.SuperHeroApiService
import com.alfonsocastro.superhero.model.Hero
import com.alfonsocastro.superhero.repository.SuperHeroRepository
import retrofit2.HttpException
import java.io.IOException

private const val SUPER_HERO_STARTING_INDEX = 1

class SuperHeroPagingSource(
    private val service: SuperHeroApiService
): PagingSource<Int, Hero>() {

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        try {
            // We get the position
            val position: Int = params.key ?: SUPER_HERO_STARTING_INDEX
            // Because the API don't have an endpoint to retur a list with pagination,
            // We call it here.
            val heroes = mutableListOf<Hero>()
            repeat(params.loadSize) {
                val hero = service.getHero(position + it)
                heroes.add(hero)
            }
            // We get the last key from the last Hero ID
            val lastHeroId = heroes.last().id.toInt()
            Log.d("SuperHeroPagingSource", "Load() lastHeroId: $lastHeroId")
            return LoadResult.Page(
                data = heroes,
                prevKey = null, // Only paging foward
                nextKey = lastHeroId
            )
        } catch (exception: IOException) {
            // IOException for network failures.
                Log.e("SuperHeroPagingSource", "Error", exception)
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            Log.e("SuperHeroPagingSource", "Error", exception)
            return LoadResult.Error(exception)
        }
    }

}