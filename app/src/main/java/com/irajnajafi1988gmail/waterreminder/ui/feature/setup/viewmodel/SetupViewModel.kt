package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.domain.model.UserProfile
import com.irajnajafi1988gmail.waterreminder.domain.usecase.GetUserUseCase
import com.irajnajafi1988gmail.waterreminder.domain.usecase.IsProfileCompleteUseCase
import com.irajnajafi1988gmail.waterreminder.domain.usecase.ResetUserUseCase
import com.irajnajafi1988gmail.waterreminder.domain.usecase.SaveUserUseCase
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.*
import com.irajnajafi1988gmail.waterreminder.ui.theme.SkyBlue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SetupVM"
private const val SAVE_DEBOUNCE_MS = 500L

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val isProfileCompleteUseCase: IsProfileCompleteUseCase,
    private val resetUserUseCase: ResetUserUseCase
) : ViewModel() {

    // -----------------------------
    // 🔹 State Flows
    // -----------------------------
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _isProfileCompleteState = MutableStateFlow<Boolean?>(null)
    val isProfileCompleteState: StateFlow<Boolean?> = _isProfileCompleteState.asStateFlow()

    private var saveJob: Job? = null
    private var isResetting = false

    // -----------------------------
    // 🔹 Step statuses for UI
    // -----------------------------
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
                    label = if (profile.weight > 0) "${profile.weight} Kg" else "Weight",
                    isActive = profile.weight > 0,
                    color = SkyBlue
                ),
                StepStatus(
                    icon = R.drawable.age,
                    label = if (profile.age > 0) "${profile.age} Yr" else "Age",
                    isActive = profile.age > 0,
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

    // -----------------------------
    // 🔹 Gender items
    // -----------------------------
    val genderItems = listOf(
        ItemGender(R.drawable.man_male, Gender.MALE.name, Gender.MALE),
        ItemGender(R.drawable.woman_female, Gender.FEMALE.name, Gender.FEMALE)
    )

    // -----------------------------
    // 🔹 Next button enabled state
    // -----------------------------
    val isNextEnabled: StateFlow<Boolean> =
        combine(_userProfile, _currentStep) { profile, step ->
            when (step) {
                0 -> profile.gender != null
                1 -> profile.weight > 0
                2 -> profile.age > 0
                3 -> profile.activity != null
                4 -> profile.environment != null
                else -> true
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // -----------------------------
    // 🔹 Init
    // -----------------------------
    init {
        Log.d(TAG, "🔥 ViewModel init STARTED")

        // Collect DataStore user profile
        viewModelScope.launch {
            getUserUseCase()
                .distinctUntilChanged()
                .collect { profile ->
                    if (!isResetting) {
                        Log.d(TAG, "⏺ Applying profile to UI state")
                        _userProfile.value = profile
                    } else {
                        Log.d(TAG, "⛔ Ignored DataStore emission due to reset")
                    }
                }
        }

        // Profile complete state
        viewModelScope.launch {
            isProfileCompleteUseCase().collect { complete ->
                Log.d(TAG, "🎯 ProfileComplete from DS = $complete")
                _isProfileCompleteState.value = complete
            }
        }
    }

    // -----------------------------
    // 🔹 Debounced save
    // -----------------------------
    private fun saveProfileDebounced(profile: UserProfile) {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            delay(SAVE_DEBOUNCE_MS)
            saveUserUseCase(profile)
            Log.d(TAG, "💾 Profile saved to DataStore: $profile")
        }
    }

    // -----------------------------
    // 🔹 Update profile
    // -----------------------------
    fun updateProfile(newProfile: UserProfile) {
        if (_userProfile.value == newProfile) return
        _userProfile.value = newProfile
        saveProfileDebounced(newProfile)
    }

    // -----------------------------
    // 🔹 Complete profile
    // -----------------------------
    fun completeProfile() {
        Log.d(TAG, "🎉 completeProfile() CALLED")

        val updatedProfile = _userProfile.value.copy(isProfileComplete = true)
        _userProfile.value = updatedProfile

        viewModelScope.launch {
            saveUserUseCase(updatedProfile)
            Log.d(TAG, "💾 Profile COMPLETED and saved: $updatedProfile")
        }
    }

    // -----------------------------
    // 🔹 Setters
    // -----------------------------
    fun setGender(gender: Gender) = updateProfile(_userProfile.value.copy(gender = gender))
    fun setWeight(weight: Int) = updateProfile(_userProfile.value.copy(weight = weight))
    fun setAge(age: Int) = updateProfile(_userProfile.value.copy(age = age))
    fun setActivity(activity: ActivityLevel) = updateProfile(_userProfile.value.copy(activity = activity))
    fun setEnvironment(environment: Environment) = updateProfile(_userProfile.value.copy(environment = environment))
    fun setCurrentStep(step: Int) { _currentStep.value = step }

    // -----------------------------
    // 🔹 Reset profile
    // -----------------------------
    // -----------------------------
// 🔹 Reset profile
// -----------------------------
    fun resetProfile() {
        viewModelScope.launch {
            val defaultProfile = UserProfile()
            Log.d(TAG, "🧨 resetProfile START")

            resetUserUseCase(defaultProfile)

            _userProfile.value = defaultProfile
            _currentStep.value = 0
            _isProfileCompleteState.value = false

            Log.d(TAG, "♻ resetProfile END")
        }
    }



}
