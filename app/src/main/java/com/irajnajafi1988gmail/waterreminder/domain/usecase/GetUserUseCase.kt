package com.irajnajafi1988gmail.waterreminder.domain.usecase

import com.irajnajafi1988gmail.waterreminder.data.repository.UserRepository
import com.irajnajafi1988gmail.waterreminder.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository

) {
    operator fun invoke(): Flow<UserProfile> = repository.getUserProfile()
}