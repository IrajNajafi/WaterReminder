package com.irajnajafi1988gmail.waterreminder.domain.model

import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ActivityLevel
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Environment
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Gender

data class UserProfile(
    val gender: Gender? = null,
    val weight: Int? = null,
    val age: Int? = null,
    val activity: ActivityLevel? = null,
    val environment: Environment? = null
)