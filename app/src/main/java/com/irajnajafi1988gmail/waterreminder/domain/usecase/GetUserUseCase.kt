package com.irajnajafi1988gmail.waterreminder.domain.usecase

import com.irajnajafi1988gmail.waterreminder.data.repository.UserRepository
import com.irajnajafi1988gmail.waterreminder.domain.model.UserProfile
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.model.ItemDishes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// ---------------------------- پروفایل کاربر ----------------------------
class GetUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Flow<UserProfile> = repository.getUserProfile()
}

class SaveUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(profile: UserProfile) = repository.saveUserProfile(profile)
}

class ResetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(defaultProfile: UserProfile) {
        repository.resetUserProfile(defaultProfile)
    }
}


class IsProfileCompleteUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Flow<Boolean> = repository.isProfileCompleteFlow()
}

// ---------------------------- مصرف آب روزانه ----------------------------
class SaveDailyWaterUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(amount: Int) = repository.saveDailyWaterDrunk(amount)
}

class GetDailyWaterUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Flow<Int> = repository.getDailyWaterDrunk()
}

// ---------------------------- آلارم ----------------------------
class SaveAlarmUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(enabled: Boolean) = repository.saveAlarm(enabled)
}

class GetAlarmUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Flow<Boolean> = repository.getAlarm()
}

// ---------------------------- انتخاب لیوان ----------------------------
class SaveSelectedDishUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(dish: ItemDishes) =
        repository.saveSelectedDish(
            icon = dish.icon,
            label = dish.label,
            volume = dish.volumeMl ?: 0
        )
}

class GetSelectedDishUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Flow<ItemDishes> = repository.getSelectedDish()
}
