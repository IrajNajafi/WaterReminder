package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.domain.usecase.*
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.model.ItemDishes
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.utils.WaterCalculatorDynamic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyWaterUseCase: GetDailyWaterUseCase,
    private val saveDailyWaterUseCase: SaveDailyWaterUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val resetUserUseCase: ResetUserUseCase,
    private val saveSelectedDishUseCase: SaveSelectedDishUseCase,
    private val getSelectedDishUseCase: GetSelectedDishUseCase,
    private val saveAlarmUseCase: SaveAlarmUseCase,
    private val getAlarmUseCase: GetAlarmUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"

        // 💧 مقادیر پیش‌فرض
        val DEFAULT_DISH = ItemDishes(R.drawable.glass175, "175 ml", 175)
        const val DEFAULT_WATER = 0
        const val DEFAULT_ALARM = false
    }

    // 💧 میزان آب خورده امروز
    private val _dailyWaterDrunk = MutableStateFlow(DEFAULT_WATER)
    val dailyWaterDrunk: StateFlow<Int> = _dailyWaterDrunk.asStateFlow()

    // 💧 هدف روزانه آب (بر اساس پروفایل)
    private val _dailyWaterGoal = MutableStateFlow(0)
    val dailyWaterGoal: StateFlow<Int> = _dailyWaterGoal.asStateFlow()

    // 🥤 انتخاب لیوان
    private val _selectedDish = MutableStateFlow(DEFAULT_DISH)
    val selectedDish: StateFlow<ItemDishes> = _selectedDish.asStateFlow()

    // 🔔 وضعیت آلارم
    private val _alarmEnabled = MutableStateFlow(DEFAULT_ALARM)
    val alarmEnabled: StateFlow<Boolean> = _alarmEnabled.asStateFlow()

    // 🍽️ Overlay‌ها
    private val _showDishes = MutableStateFlow(false)
    val showDishes: StateFlow<Boolean> = _showDishes.asStateFlow()

    private val _showAlarm = MutableStateFlow(false)
    val showAlarm: StateFlow<Boolean> = _showAlarm.asStateFlow()

    // 🥤 لیست آیتم‌های لیوان/دیس‌ها
    val itemDishes = listOf(
        ItemDishes(R.drawable.cup100, "100 ml", 100),
        ItemDishes(R.drawable.cup125, "125 ml", 125),
        ItemDishes(R.drawable.glass175, "175 ml", 175),
        ItemDishes(R.drawable.mug200, "200 ml", 200),
        ItemDishes(R.drawable.bottle, "250 ml", 250),
        ItemDishes(R.drawable.thermos500, "500 ml", 500),
        ItemDishes(R.drawable.thermos1000, "1000 ml", 1000)
    )

    init {
        // دریافت هدف روزانه از پروفایل کاربر
        viewModelScope.launch {
            getUserUseCase().collect { profile ->
                val calculatedGoal = WaterCalculatorDynamic.calculateDailyNeedMl(profile)
                _dailyWaterGoal.value = calculatedGoal
            }
        }

        // دریافت میزان آب خورده امروز
        viewModelScope.launch {
            getDailyWaterUseCase().collect { drunk ->
                _dailyWaterDrunk.value = drunk
            }
        }

        // دریافت آخرین لیوان انتخاب شده
        viewModelScope.launch {
            getSelectedDishUseCase().collect { dish ->
                _selectedDish.value = if (dish.volumeMl != null && dish.volumeMl > 0) {
                    dish
                } else {
                    DEFAULT_DISH
                }
            }
        }

        // دریافت وضعیت آلارم
        viewModelScope.launch {
            getAlarmUseCase().collect { enabled ->
                _alarmEnabled.value = enabled
                Log.d(TAG, "Alarm enabled: $enabled")
            }
        }
    }

    // 💧 ذخیره میزان آب
    fun saveDailyWater(amount: Int) {
        _dailyWaterDrunk.value = amount
        Log.d(TAG, "Daily water updated: $amount ml")
        viewModelScope.launch { saveDailyWaterUseCase(amount) }
    }

    // 🥤 انتخاب لیوان
    fun selectDish(dish: ItemDishes) {
        _selectedDish.value = dish
        Log.d(TAG, "Dish selected: ${dish.label} - ${dish.volumeMl ?: "Custom"}")
        viewModelScope.launch { saveSelectedDishUseCase(dish) }
    }

    // 🍽️ نمایش/عدم نمایش لیوان‌ها
    fun toggleDishes() {
        _showDishes.value = !_showDishes.value
        _showAlarm.value = false
        Log.d(TAG, "Show dishes: ${_showDishes.value}")
    }

    // 🔔 نمایش/عدم نمایش آلارم
    fun toggleAlarm() {
        _showAlarm.value = !_showAlarm.value
        _showDishes.value = false
        Log.d(TAG, "Show alarm: ${_showAlarm.value}")
    }

    // 🔔 فعال/غیرفعال کردن آلارم
    fun setAlarm(enabled: Boolean) {
        _alarmEnabled.value = enabled
        Log.d(TAG, "Alarm set to: $enabled")
        viewModelScope.launch { saveAlarmUseCase(enabled) }
    }

    // ♻️ ریست کامل همه چیز
    fun resetAll() {
        viewModelScope.launch {
            try {
                // ریست DataStore
                saveDailyWaterUseCase(DEFAULT_WATER)
                saveAlarmUseCase(DEFAULT_ALARM)
                saveSelectedDishUseCase(DEFAULT_DISH)

                // ریست همزمان StateFlowها
                _dailyWaterDrunk.value = DEFAULT_WATER
                _alarmEnabled.value = DEFAULT_ALARM
                _selectedDish.value = DEFAULT_DISH

                Log.d(TAG, "✅ Reset all done")
            } catch (e: Exception) {
                Log.e(TAG, "❌ Error resetting Home", e)
            }
        }
    }

}
