package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ActivityLevel
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Environment
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Gender
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ItemGender
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.StepStatus
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.UserProfile
import com.irajnajafi1988gmail.waterreminder.ui.theme.SkyBlue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
        combine(_userProfile, _currentStep) { profile, _ ->
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
                    isActive = profile.weight != null,
                    color = SkyBlue
                ),
                StepStatus(
                    icon = R.drawable.age,
                    label = profile.age?.let { "$it Yr" } ?: "Age",
                    isActive = profile.age != null,
                    color = SkyBlue
                ),
                StepStatus(
                    icon = R.drawable.activity,
                    label = profile.activity?.name ?: "Activity",
                    isActive = profile.activity != null,
                    color = SkyBlue
                ),
                StepStatus(
                    icon = R.drawable.environment,
                    label = profile.environment?.name ?: "Environment",
                    isActive = profile.environment != null,
                    color = SkyBlue
                )
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Gender Items ---
    val genderItems = listOf(
        ItemGender(R.drawable.man_male, Gender.MALE.name, Gender.MALE),
        ItemGender(R.drawable.woman_female, Gender.FEMALE.name, Gender.FEMALE)
    )

    // --- Next Enabled ---
    val isNextEnabled: StateFlow<Boolean> =
        combine(_userProfile, _currentStep) { profile, step ->
            when(step) {
                0 -> profile.gender != null
                1 -> (profile.weight ?: 0) > 0
                2 -> (profile.age ?: 0) > 0
                3 -> profile.activity != null
                4 -> profile.environment != null
                else -> true
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // --- Update functions ---
    fun setGender(gender: Gender) = update { it.copy(gender = gender) }
    fun setWeight(weight: Int) = update { it.copy(weight = weight) }
    fun setAge(age: Int) = update { it.copy(age = age) }
    fun setActivity(level: ActivityLevel) = update { it.copy(activity = level) }
    fun setEnvironment(env: Environment) = update { it.copy(environment = env) }
    fun setCurrentStep(step: Int) { _currentStep.value = step }

    private fun update(block: (UserProfile) -> UserProfile) {
        _userProfile.value = block(_userProfile.value)
    }
}
