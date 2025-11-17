package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor() : ViewModel() {

    // داده اصلی کاربر
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // بررسی کامل بودن پروفایل (مثلاً برای فعال کردن دکمه Next)
    val isProfileComplete: StateFlow<Boolean> = _userProfile
        .map { profile ->
            profile.weight > 0 &&
                    profile.age > 0 &&
                    profile.gender != null &&
                    profile.activity != null &&
                    profile.environment != null
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    // توابع به‌روزرسانی هر فیلد
    fun updateGender(gender: Gender) {
        _userProfile.value = _userProfile.value.copy(gender = gender)
    }

    fun updateWeight(weight: Int) {
        _userProfile.value = _userProfile.value.copy(weight = weight)
    }

    fun updateAge(age: Int) {
        _userProfile.value = _userProfile.value.copy(age = age)
    }

    fun updateActivityLevel(activity: ActivityLevel) {
        _userProfile.value = _userProfile.value.copy(activity = activity)
    }

    fun updateEnvironment(environment: Environment) {
        _userProfile.value = _userProfile.value.copy(environment = environment)
    }
}