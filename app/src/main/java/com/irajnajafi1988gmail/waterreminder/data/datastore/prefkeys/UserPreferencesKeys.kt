package com.irajnajafi1988gmail.waterreminder.data.datastore.prefkeys

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesKeys {
    const val USER_PROFILE_NAME = "user_profile_prefs"
    val GENDER_KEY = stringPreferencesKey("gender")
    val WEIGHT_KEY = intPreferencesKey("weight")
    val AGE_KEY = intPreferencesKey("age")
    val ACTIVITY_KEY = stringPreferencesKey("activity")
    val ENVIRONMENT_KEY = stringPreferencesKey("environment")
    val PROFILE_COMPLETE_KEY = booleanPreferencesKey("profile_complete")


}