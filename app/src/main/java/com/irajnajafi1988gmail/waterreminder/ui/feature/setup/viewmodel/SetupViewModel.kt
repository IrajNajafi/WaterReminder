package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.*
import com.irajnajafi1988gmail.waterreminder.ui.theme.SkyBlue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SetupViewModel : ViewModel() {

    // وضعیت کاربر
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    // مرحله فعلی
    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep

    // تولید لیست مراحل برای UI
    val stepStatuses: StateFlow<List<StepStatus>> = MutableStateFlow(emptyList())

    init {
        updateSteps()
    }

    private fun updateSteps() {
        viewModelScope.launch {
            val profile = _userProfile.value
            val stepList = listOf(
                StepStatus(
                    icon = if (profile.gender == Gender.MALE) R.drawable.male else R.drawable.female,
                    label = profile.gender?.name ?: "",
                    color = SkyBlue,
                    isActive = _currentStep.value >= 0
                ),
                StepStatus(
                    icon = R.drawable.weight,
                    label = "${profile.weight} Kg",
                    color = SkyBlue,
                    isActive = _currentStep.value >= 1
                ),
                StepStatus(
                    icon = R.drawable.age,
                    label = "${profile.age} Yr",
                    color = SkyBlue,
                    isActive = _currentStep.value >= 2
                ),
                StepStatus(
                    icon = R.drawable.activity,
                    label = profile.activity?.name ?: "",
                    color = SkyBlue,
                    isActive = _currentStep.value >= 3
                ),
                StepStatus(
                    icon = R.drawable.environment,
                    label = profile.environment?.name ?: "",
                    color = SkyBlue,
                    isActive = _currentStep.value >= 4
                )
            )
            (stepStatuses as MutableStateFlow).value = stepList
        }
    }

    // توابع تغییر وضعیت کاربر
    fun setGender(gender: Gender) {
        _userProfile.value = _userProfile.value.copy(gender = gender)
        updateSteps()
    }

    fun setWeight(weight: Int) {
        _userProfile.value = _userProfile.value.copy(weight = weight)
        updateSteps()
    }

    fun setAge(age: Int) {
        _userProfile.value = _userProfile.value.copy(age = age)
        updateSteps()
    }

    fun setActivity(activity: ActivityLevel) {
        _userProfile.value = _userProfile.value.copy(activity = activity)
        updateSteps()
    }

    fun setEnvironment(environment: Environment) {
        _userProfile.value = _userProfile.value.copy(environment = environment)
        updateSteps()
    }

    fun setCurrentStep(step: Int) {
        _currentStep.value = step
        updateSteps()
    }
}
