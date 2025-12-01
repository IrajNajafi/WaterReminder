package com.irajnajafi1988gmail.waterreminder.data.datastore.prefkeys

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesKeys {
    const val USER_PROFILE_NAME = "user_profile_prefs"

    // ---------- User Profile ----------
    val GENDER_KEY = stringPreferencesKey("gender")
    val WEIGHT_KEY = intPreferencesKey("weight")
    val AGE_KEY = intPreferencesKey("age")
    val ACTIVITY_KEY = stringPreferencesKey("activity")
    val ENVIRONMENT_KEY = stringPreferencesKey("environment")
    val PROFILE_COMPLETE_KEY = booleanPreferencesKey("profile_complete")

    // ---------- Daily Water ----------
    val DAILY_WATER_DRUNK_KEY = intPreferencesKey("daily_water_drunk")

    // ---------- Alarm ----------
    val ALARM_ENABLED_KEY = booleanPreferencesKey("alarm_enabled")
    val ALARM_HOUR_KEY = intPreferencesKey("alarm_hour")
    val ALARM_MINUTE_KEY = intPreferencesKey("alarm_minute")

    // ---------- Selected Cup / Dish ----------
    val SELECTED_DISH_ICON_KEY = intPreferencesKey("selected_dish_icon")
    val SELECTED_DISH_VOLUME_KEY = intPreferencesKey("selected_dish_volume")
    val SELECTED_DISH_LABEL_KEY = stringPreferencesKey("selected_dish_label")

    // ---------- Future / Extras ----------
    // می‌تونی اینجا کلیدهای دیگه هم اضافه کنی مثل یادآوری‌های سفارشی یا تم اپ
}
