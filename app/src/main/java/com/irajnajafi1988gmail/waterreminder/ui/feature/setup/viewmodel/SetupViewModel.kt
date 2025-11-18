package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.*
import com.irajnajafi1988gmail.waterreminder.ui.theme.SkyBlue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor() : ViewModel() {

    // --- User Profile ---
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile = _userProfile.asStateFlow()

    // --- Current Step ---
    private val _currentStep = MutableStateFlow(0)
    val currentStep = _currentStep.asStateFlow()

    // --- Step Statuses ---
    val stepStatuses: StateFlow<List<StepStatus>> =
        combine(_userProfile, _currentStep) { profile, step ->
            listOf(
                StepStatus(
                    icon = when(profile.gender) {
                        Gender.MALE -> R.drawable.male
                        Gender.FEMALE -> R.drawable.female
                        else -> R.drawable.venus_mars_icon
                    },
                    label = profile.gender?.name ?: "Gender",
                    isActive = profile.gender != null,
                    color = SkyBlue
                ),
                StepStatus(
                    icon = R.drawable.weight,
                    label = profile.weight?.let { "$it Kg" } ?: "Weight",
                    color = SkyBlue,
                    isActive = profile.weight != null
                ),
                StepStatus(
                    icon = R.drawable.age,
                    label = profile.age?.let { "$it Yr" } ?: "Age",
                    color = SkyBlue,
                    isActive = profile.age != null
                ),
                StepStatus(
                    icon = R.drawable.activity,
                    label = profile.activity?.name ?: "Activity",
                    color = SkyBlue,
                    isActive = profile.activity != null
                ),
                StepStatus(
                    icon = R.drawable.environment,
                    label = profile.environment?.name ?: "Environment",
                    color = SkyBlue,
                    isActive = profile.environment != null
                )
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    // --- Update functions ---
    fun setGender(gender: Gender) =
        update { it.copy(gender = gender) }

    fun setWeight(weight: Int) =
        update { it.copy(weight = weight) }

    fun setAge(age: Int) =
        update { it.copy(age = age) }

    fun setActivity(level: ActivityLevel) =
        update { it.copy(activity = level) }

    fun setEnvironment(env: Environment) =
        update { it.copy(environment = env) }

    fun setCurrentStep(step: Int) {
        _currentStep.value = step
    }

    private fun update(block: (UserProfile) -> UserProfile) {
        _userProfile.value = block(_userProfile.value)
    }
}
