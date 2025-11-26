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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.get

@Singleton
class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserRepository {

    override fun getUserProfile(): Flow<UserProfile> =
        context.userPreferencesDataStore.data
            .map { preferences ->

                val gender = preferences[UserPreferencesKeys.GENDER_KEY]?.let {
                    Gender.valueOf(it)
                }

                val weight = preferences[UserPreferencesKeys.WEIGHT_KEY]
                val age = preferences[UserPreferencesKeys.AGE_KEY]

                val activity = preferences[UserPreferencesKeys.ACTIVITY_KEY]?.let {
                    ActivityLevel.valueOf(it)
                }

                val environment = preferences[UserPreferencesKeys.ENVIRONMENT_KEY]?.let {
                    Environment.valueOf(it)
                }

                val profile = UserProfile(
                    gender = gender,
                    weight = weight,
                    age = age,
                    activity = activity,
                    environment = environment
                )

                Log.d("UserRepositoryImpl", "📦 Loaded profile: $profile")
                profile
            }

    override suspend fun saveUserProfile(profile: UserProfile) {
        context.userPreferencesDataStore.edit { prefs ->
            profile.gender?.let {
                prefs[UserPreferencesKeys.GENDER_KEY] = it.name
            }
            profile.weight?.let {
                prefs[UserPreferencesKeys.WEIGHT_KEY] = it
            }
            profile.age?.let {
                prefs[UserPreferencesKeys.AGE_KEY] = it
            }
            profile.activity?.let {
                prefs[UserPreferencesKeys.ACTIVITY_KEY] = it.name
            }
            profile.environment?.let {
                prefs[UserPreferencesKeys.ENVIRONMENT_KEY] = it.name
            }
        }
    }

    override suspend fun resetUserProfile() {
        context.userPreferencesDataStore.edit { prefs ->
            prefs.clear()
        }
    }

    override fun isProfileCompleteFlow(): Flow<Boolean> =
        context.userPreferencesDataStore.data
            .map { preferences ->
                val complete = preferences[UserPreferencesKeys.PROFILE_COMPLETE_KEY] ?: false
                Log.d("UserRepositoryImpl", "✅ Profile complete status: $complete")
                complete
            }
}
