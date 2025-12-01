package com.irajnajafi1988gmail.waterreminder.domain.model

import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ActivityLevel
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Environment
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Gender

data class UserProfile(
    val gender: Gender? = Gender.MALE,
    val weight: Int =0,
    val age: Int = 0,
    val activity: ActivityLevel? = ActivityLevel.MEDIUM,
    val environment: Environment? = Environment.NORMAL,
    val dailyWaterDrunk: Int = 0, // مقدار آب خورده
    val isProfileComplete: Boolean = false // آیا پروفایل کامل شده؟
)
