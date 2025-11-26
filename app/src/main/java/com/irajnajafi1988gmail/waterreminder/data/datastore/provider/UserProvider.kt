package com.irajnajafi1988gmail.waterreminder.data.datastore.provider


import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.irajnajafi1988gmail.waterreminder.data.datastore.prefkeys.UserPreferencesKeys

val Context.userPreferencesDataStore by preferencesDataStore(name = UserPreferencesKeys.USER_PROFILE_NAME)