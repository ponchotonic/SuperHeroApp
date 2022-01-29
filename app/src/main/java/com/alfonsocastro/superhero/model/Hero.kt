package com.alfonsocastro.superhero.model

import com.squareup.moshi.Json

data class Hero(
    val response: String,
    val id: String,
    val name: String,
    @Json(name = "powerstats") val powerStats: HeroPowerStats,
    val biography: HeroBiography,
    val appearance: HeroAppearance,
    val work: HeroWork,
    val connections: HeroConnections,
    val image: HeroImage
)
