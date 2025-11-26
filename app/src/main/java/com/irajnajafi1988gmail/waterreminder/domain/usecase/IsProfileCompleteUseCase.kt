package com.irajnajafi1988gmail.waterreminder.domain.usecase

import com.irajnajafi1988gmail.waterreminder.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsProfileCompleteUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.isProfileCompleteFlow()
    }
}
