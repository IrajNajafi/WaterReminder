package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model


data class UserProfile(
    val gender: Gender? = null,
    val weight: Int? = null,
    val age: Int? = null,
    val activity: ActivityLevel? = null,
    val environment: Environment? = null
)
