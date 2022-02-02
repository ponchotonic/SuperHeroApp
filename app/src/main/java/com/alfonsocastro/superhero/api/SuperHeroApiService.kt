package com.alfonsocastro.superhero.api

import android.util.Log
import androidx.paging.PagingSource
import com.alfonsocastro.superhero.model.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// Access Token 461455927790948

private const val BASE_URL = "https://superheroapi.com/api/"
private const val TOKEN = "461455927790948"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("$BASE_URL$TOKEN/")
    .build()

/**
 * A public interface that exposes the [getHero] method
 */
interface SuperHeroApiService {

    /**
     * Returns a [Hero] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "id" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("{id}")
    suspend fun getHero(@Path("id") heroId: Int): Hero

    /**
     * Returns a [Hero] list and this method can be called from a Coroutine.
     * Sends a @GET request for the  search by name API endpoint with the given [heroName]
     */
    @GET("search/{name}")
    suspend fun searchHeroName(@Path("name") heroName: String): List<Hero>

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object SuperHeroApi {
    val retrofitService: SuperHeroApiService by lazy {
        retrofit.create(SuperHeroApiService::class.java)
    }
}