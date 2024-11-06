// Copyright 2023 Citra Emulator Project
// Copyright 2024 Borked3DS Emulator Project
// Licensed under GPLv2 or any later version
// Refer to the license.txt file included.

package io.github.borked3ds.android.features.settings.model.view

import io.github.borked3ds.android.features.settings.model.AbstractFloatSetting
import io.github.borked3ds.android.features.settings.model.AbstractIntSetting
import io.github.borked3ds.android.features.settings.model.AbstractSetting
import io.github.borked3ds.android.features.settings.model.FloatSetting
import io.github.borked3ds.android.features.settings.model.ScaledFloatSetting
import io.github.borked3ds.android.utils.Log
import kotlin.math.roundToInt

class SliderSetting(
    setting: AbstractSetting?,
    titleId: Int,
    descriptionId: Int,
    val min: Int,
    val max: Int,
    val units: String,
    val key: String? = null,
    val defaultValue: Float? = null
) : SettingsItem(setting, titleId, descriptionId) {
    override val type = TYPE_SLIDER

    val selectedFloat: Float
        get() {
            val setting = setting ?: return defaultValue?.toFloat() ?: -1f
            return when (setting) {
                is AbstractIntSetting -> setting.int.toFloat()
                is FloatSetting -> setting.float
                is ScaledFloatSetting -> setting.float
                else -> {
                    Log.error("[SliderSetting] Error casting setting type.")
                    -1f
                }
            }
        }

    /**
     * Write a value to the backing int. If that int was previously null,
     * initializes a new one and returns it, so it can be added to the Hashmap.
     *
     * @param selection New value of the int.
     * @return the existing setting with the new value applied.
     */
    fun setSelectedValue(selection: Int): AbstractIntSetting {
        val intSetting = setting as? AbstractIntSetting
            ?: throw IllegalStateException("Setting is not an AbstractIntSetting")
        intSetting.int = selection
        return intSetting
    }

    /**
     * Write a value to the backing float. If that float was previously null,
     * initializes a new one and returns it, so it can be added to the Hashmap.
     *
     * @param selection New value of the float.
     * @return the existing setting with the new value applied.
     */
    fun setSelectedValue(selection: Float): AbstractFloatSetting {
        val floatSetting = setting as? AbstractFloatSetting
            ?: throw IllegalStateException("Setting is not an AbstractFloatSetting")
        floatSetting.float = selection
        return floatSetting
    }
}
