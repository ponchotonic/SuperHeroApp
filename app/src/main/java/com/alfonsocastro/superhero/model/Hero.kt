package com.alfonsocastro.superhero.model

import com.squareup.moshi.Json

data class Hero(
    val response: String,
    val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "biography") val biography: HeroBiography,
    @Json(name = "image") val image: HeroImage
)
