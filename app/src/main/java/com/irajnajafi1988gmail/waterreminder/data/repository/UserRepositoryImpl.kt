package com.irajnajafi1988gmail.waterreminder.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.irajnajafi1988gmail.waterreminder.data.datastore.prefkeys.UserPreferencesKeys
import com.irajnajafi1988gmail.waterreminder.data.datastore.provider.userPreferencesDataStore
import com.irajnajafi1988gmail.waterreminder.domain.model.UserProfile
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ActivityLevel
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Environment
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Gender
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.model.ItemDishes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserRepository {
    private var isResetting = false


    override fun getUserProfile(): Flow<UserProfile> =
        context.userPreferencesDataStore.data
            .map { preferences ->
                // اگر ریست در حال اجراست، emissionها نادیده گرفته شوند
                if (isResetting) {
                    Log.d("UserRepositoryImpl", "⛔ Ignored DataStore emission due to reset")
                    return@map UserProfile(
                        gender = Gender.MALE,
                        weight = 50,
                        age = 25,
                        activity = ActivityLevel.MEDIUM,
                        environment = Environment.NORMAL,
                        isProfileComplete = false
                    )
                }

                val gender = preferences[UserPreferencesKeys.GENDER_KEY]?.let {
                    Gender.valueOf(it)
                } ?: Gender.MALE

                val weight = preferences[UserPreferencesKeys.WEIGHT_KEY] ?: 50
                val age = preferences[UserPreferencesKeys.AGE_KEY] ?: 25

                val activity = preferences[UserPreferencesKeys.ACTIVITY_KEY]?.let {
                    ActivityLevel.valueOf(it)
                } ?: ActivityLevel.MEDIUM

                val environment = preferences[UserPreferencesKeys.ENVIRONMENT_KEY]?.let {
                    Environment.valueOf(it)
                } ?: Environment.NORMAL

                val isComplete = preferences[UserPreferencesKeys.PROFILE_COMPLETE_KEY] ?: false

                val profile = UserProfile(
                    gender = gender,
                    weight = weight,
                    age = age,
                    activity = activity,
                    environment = environment,
                    isProfileComplete = isComplete
                )

                Log.d("UserRepositoryImpl", "📦 Loaded profile: $profile")
                profile
            }

    override suspend fun saveUserProfile(profile: UserProfile) {
        val isComplete = profile.gender != null &&
                profile.weight > 0 &&
                profile.age > 0 &&
                profile.activity != null &&
                profile.environment != null

        Log.d("UserRepositoryImpl", "💾 Saving profile: $profile | Complete: $isComplete")

        context.userPreferencesDataStore.edit { pref ->
            pref[UserPreferencesKeys.GENDER_KEY] = profile.gender?.name ?: Gender.MALE.name
            pref[UserPreferencesKeys.WEIGHT_KEY] = profile.weight
            pref[UserPreferencesKeys.AGE_KEY] = profile.age
            pref[UserPreferencesKeys.ACTIVITY_KEY] = profile.activity?.name ?: ActivityLevel.MEDIUM.name
            pref[UserPreferencesKeys.ENVIRONMENT_KEY] = profile.environment?.name ?: Environment.NORMAL.name
            pref[UserPreferencesKeys.PROFILE_COMPLETE_KEY] = profile.isProfileComplete
        }
    }

    override suspend fun resetUserProfile(defaultProfile: UserProfile) {
        context.userPreferencesDataStore.edit { prefs ->
            prefs.clear()
            prefs[UserPreferencesKeys.GENDER_KEY] = defaultProfile.gender?.name ?: Gender.MALE.name
            prefs[UserPreferencesKeys.WEIGHT_KEY] = defaultProfile.weight
            prefs[UserPreferencesKeys.AGE_KEY] = defaultProfile.age
            prefs[UserPreferencesKeys.ACTIVITY_KEY] = defaultProfile.activity?.name ?: ActivityLevel.MEDIUM.name
            prefs[UserPreferencesKeys.ENVIRONMENT_KEY] = defaultProfile.environment?.name ?: Environment.NORMAL.name
            prefs[UserPreferencesKeys.PROFILE_COMPLETE_KEY] = defaultProfile.isProfileComplete
            prefs[UserPreferencesKeys.DAILY_WATER_DRUNK_KEY] = defaultProfile.dailyWaterDrunk
        }
        Log.d("UserRepositoryImpl", "♻ User profile RESET and default saved: $defaultProfile")
    }



    override fun isProfileCompleteFlow(): Flow<Boolean> =
        context.userPreferencesDataStore.data
            .map { preferences ->
                preferences[UserPreferencesKeys.PROFILE_COMPLETE_KEY] ?: false
            }

    override suspend fun saveDailyWaterDrunk(amount: Int) {
        context.userPreferencesDataStore.edit { prefs ->
            prefs[UserPreferencesKeys.DAILY_WATER_DRUNK_KEY] = amount
        }
        Log.d("UserRepositoryImpl", "💧 Daily water drunk saved: $amount ml")
    }

    override fun getDailyWaterDrunk(): Flow<Int> =
        context.userPreferencesDataStore.data
            .map { preferences ->
                preferences[UserPreferencesKeys.DAILY_WATER_DRUNK_KEY] ?: 0
            }

    override suspend fun saveAlarm(enabled: Boolean) {
        context.userPreferencesDataStore.edit { prefs ->
            prefs[UserPreferencesKeys.ALARM_ENABLED_KEY] = enabled
        }
        Log.d("UserRepositoryImpl", "⏰ Alarm enabled: $enabled")
    }

    override fun getAlarm(): Flow<Boolean> =
        context.userPreferencesDataStore.data.map { prefs ->
            prefs[UserPreferencesKeys.ALARM_ENABLED_KEY] ?: false
        }

    override suspend fun saveSelectedDish(icon: Int, volume: Int, label: String) {
        context.userPreferencesDataStore.edit { prefs ->
            prefs[UserPreferencesKeys.SELECTED_DISH_ICON_KEY] = icon
            prefs[UserPreferencesKeys.SELECTED_DISH_VOLUME_KEY] = volume
            prefs[UserPreferencesKeys.SELECTED_DISH_LABEL_KEY] = label
        }
    }


    override fun getSelectedDish(): Flow<ItemDishes> =
        context.userPreferencesDataStore.data.map { prefs ->
            val icon = prefs[UserPreferencesKeys.SELECTED_DISH_ICON_KEY] ?: 0
            val volume = prefs[UserPreferencesKeys.SELECTED_DISH_VOLUME_KEY] ?: 0
            val label = prefs[UserPreferencesKeys.SELECTED_DISH_LABEL_KEY] ?: "Default"

            ItemDishes(
                icon = icon,
                label = label,
                volumeMl = volume
            )
        }

}
