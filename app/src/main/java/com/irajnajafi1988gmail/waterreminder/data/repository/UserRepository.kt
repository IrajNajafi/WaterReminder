package com.irajnajafi1988gmail.waterreminder.data.repository

import com.irajnajafi1988gmail.waterreminder.domain.model.UserProfile
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.model.ItemDishes
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserProfile(): Flow<UserProfile>
    suspend fun saveUserProfile(profile: UserProfile)
    suspend fun resetUserProfile(defaultProfile: UserProfile) // ← اضافه شد
    fun isProfileCompleteFlow(): Flow<Boolean>
    suspend fun saveDailyWaterDrunk(amount: Int)
    fun getDailyWaterDrunk(): Flow<Int>
    suspend fun saveAlarm(enabled: Boolean)
    fun getAlarm(): Flow<Boolean>
    suspend fun saveSelectedDish(icon: Int, volume: Int, label: String)
    fun getSelectedDish(): Flow<ItemDishes>
}
