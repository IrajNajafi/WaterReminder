package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.utils

import com.irajnajafi1988gmail.waterreminder.domain.model.UserProfile
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.*

object WaterCalculatorDynamic {

    /**
     * محاسبه نیاز روزانه آب با روش پیشرفته
     */
    fun calculateDailyNeedMl(user: UserProfile): Int {
        // اگر وزن یا سن null باشه، محاسبه انجام نمیشه
        val weight = user.weight ?: return 0
        val age = user.age ?: return 0

        // 1. پایه بر اساس وزن: 30 میلی‌لیتر به ازای هر کیلوگرم
        var base = weight * 30f

        // 2. ضریب جنسیت
        base *= when (user.gender) {
            Gender.MALE -> 1.1f
            Gender.FEMALE -> 1.0f

            null -> 1.0f

        }

        // 3. اصلاح سن
        val ageAdj = when {
            age < 10 -> 0.8f
            age in 10..17 -> 0.9f
            age in 18..55 -> 1.0f
            age > 55 -> 0.95f
            else -> 1.0f
        }
        base *= ageAdj

        // 4. فعالیت روزانه (میلی‌لیتر اضافه)
        val activityAdded = when (user.activity) {
            ActivityLevel.LOW -> 0f
            ActivityLevel.MEDIUM -> 400f
            ActivityLevel.HIGH -> 800f
            ActivityLevel.EXTREME -> 1200f
            null -> 0f
        }

        // 5. محیط زندگی و دما (میلی‌لیتر اضافه)
        val envAdded = when (user.environment) {

            Environment.FREEZING -> 100f
            Environment.COLD -> 0f
            Environment.NORMAL -> 200f
            Environment.WARM -> 300f
            Environment.HOT -> 400f
            Environment.VERY_HOT -> 600f
            null -> 0f
        }

        // جمع آب با فعالیت و محیط
        val subtotal = base + activityAdded + envAdded

        // 6. محاسبه تعریق تقریبی به درصد
        val sweatPercent = ((when (user.activity) {
            ActivityLevel.LOW -> 0f
            ActivityLevel.MEDIUM -> 5f
            ActivityLevel.HIGH -> 10f
            ActivityLevel.EXTREME -> 15f
            null -> 0f
        }) + (when (user.environment) {
            Environment.FREEZING, Environment.COLD -> 0f
            Environment.NORMAL -> 5f
            Environment.WARM -> 10f
            Environment.HOT -> 15f
            Environment.VERY_HOT -> 25f
            null -> 0f
        })) / 100f

        val total = subtotal * (1 + sweatPercent)
       // گرد کردن به نزدیک‌ترین مضرب 250
        val rounded = (total / 250).toInt() * 250

       // محدودیت نهایی
        return rounded.coerceIn(1200, 5000)

    }
}
