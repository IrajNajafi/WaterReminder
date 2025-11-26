package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.*
import com.irajnajafi1988gmail.waterreminder.domain.model.UserProfile
import com.irajnajafi1988gmail.waterreminder.domain.usecase.GetUserUseCase
import com.irajnajafi1988gmail.waterreminder.domain.usecase.IsProfileCompleteUseCase
import com.irajnajafi1988gmail.waterreminder.domain.usecase.SaveUserUseCase
import com.irajnajafi1988gmail.waterreminder.ui.theme.SkyBlue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UserViewModel"

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val isProfileCompleteUseCase: IsProfileCompleteUseCase
) : ViewModel() {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    val stepStatuses: StateFlow<List<StepStatus>> =
        combine(_userProfile, _currentStep) { profile, _ ->
            listOf(
                StepStatus(
                    icon = when (profile.gender) {
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

    val genderItems = listOf(
        ItemGender(R.drawable.man_male, Gender.MALE.name, Gender.MALE),
        ItemGender(R.drawable.woman_female, Gender.FEMALE.name, Gender.FEMALE)
    )

    val isNextEnabled: StateFlow<Boolean> =
        combine(_userProfile, _currentStep) { profile, step ->
            when (step) {
                0 -> profile.gender != null
                1 -> (profile.weight ?: 0) > 0
                2 -> (profile.age ?: 0) > 0
                3 -> profile.activity != null
                4 -> profile.environment != null
                else -> true
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isProfileComplete: StateFlow<Boolean> =
        isProfileCompleteUseCase().stateIn(viewModelScope, SharingStarted.Eagerly, false)

    init {
        viewModelScope.launch {
            getUserUseCase().collectLatest { profile ->
                _userProfile.value = profile
            }
        }
    }

    private fun updateProfile(block: (UserProfile) -> UserProfile) {
        val updatedProfile = block(_userProfile.value)
        _userProfile.value = updatedProfile
        viewModelScope.launch {
            saveUserUseCase(updatedProfile)
        }
    }

    fun setGender(gender: Gender) = updateProfile { it.copy(gender = gender) }
    fun setWeight(weight: Int) = updateProfile { it.copy(weight = weight) }
    fun setAge(age: Int) = updateProfile { it.copy(age = age) }
    fun setActivity(level: ActivityLevel) = updateProfile { it.copy(activity = level) }
    fun setEnvironment(env: Environment) = updateProfile { it.copy(environment = env) }
    fun setCurrentStep(step: Int) { _currentStep.value = step }

    fun resetUserProfile() {
        viewModelScope.launch {
            saveUserUseCase(UserProfile())
            _userProfile.value = UserProfile()
            _currentStep.value = 0
        }
    }
}
