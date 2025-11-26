package com.irajnajafi1988gmail.waterreminder.domain.usecase

import com.irajnajafi1988gmail.waterreminder.data.repository.UserRepository
import com.irajnajafi1988gmail.waterreminder.domain.model.UserProfile
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(profile: UserProfile) = repository.saveUserProfile(profile)
}