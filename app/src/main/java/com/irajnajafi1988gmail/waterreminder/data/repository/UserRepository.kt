package com.irajnajafi1988gmail.waterreminder.data.repository

import com.irajnajafi1988gmail.waterreminder.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserProfile(): Flow<UserProfile>

    suspend fun saveUserProfile(profile: UserProfile)

    suspend fun resetUserProfile()
    fun isProfileCompleteFlow(): Flow<Boolean>
}