// Copyright 2023 Citra Emulator Project
// Copyright 2024 Borked3DS Emulator Project
// Licensed under GPLv2 or any later version
// Refer to the license.txt file included.

package io.github.borked3ds.android.features.settings.model.view

import io.github.borked3ds.android.features.settings.model.AbstractIntSetting
import io.github.borked3ds.android.features.settings.model.AbstractSetting
import io.github.borked3ds.android.features.settings.model.AbstractShortSetting

class SingleChoiceSetting(
    setting: AbstractSetting?,
    titleId: Int,
    descriptionId: Int,
    val choicesId: Int,
    val valuesId: Int,
    val key: String? = null,
    val defaultValue: Int? = null
) : SettingsItem(setting, titleId, descriptionId) {
    override val type = TYPE_SINGLE_CHOICE

    val selectedValue: Int
        get() {
            if (setting == null) {
                return defaultValue ?: 0 
            }

            return try {
                val intSetting = setting as? AbstractIntSetting
                intSetting?.int ?: defaultValue ?: 0 
            } catch (_: ClassCastException) {
                try {
                    val shortSetting = setting as? AbstractShortSetting
                    shortSetting?.short?.toInt() ?: defaultValue ?: 0 
                } catch (_: ClassCastException) {
                    defaultValue ?: 0 
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

    fun setSelectedValue(selection: Short): AbstractShortSetting {
        val shortSetting = setting as? AbstractShortSetting
            ?: throw IllegalStateException("Setting is not an AbstractShortSetting")
        shortSetting.short = selection
        return shortSetting
    }
}
