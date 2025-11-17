package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model


data class UserProfile(
    val gender: Gender = Gender.MALE,
    val weight: Int = 0,
    val age: Int = 0,
    val activity: ActivityLevel = ActivityLevel.MEDIUM,
    val environment: Environment = Environment.NORMAL
)
