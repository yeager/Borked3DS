// Copyright 2023 Citra Emulator Project
// Copyright 2024 Borked3DS Emulator Project
// Licensed under GPLv2 or any later version
// Refer to the license.txt file included.

package io.github.borked3ds.android.features.settings.model.view

import io.github.borked3ds.android.features.settings.model.AbstractSetting
import io.github.borked3ds.android.features.settings.model.AbstractShortSetting
import io.github.borked3ds.android.features.settings.model.AbstractStringSetting

class StringSingleChoiceSetting(
    setting: AbstractSetting?,
    titleId: Int,
    descriptionId: Int,
    val choices: Array<String>,
    val values: Array<String>?,
    val key: String? = null,
    private val defaultValue: String? = null
) : SettingsItem(setting, titleId, descriptionId) {
    override val type = TYPE_STRING_SINGLE_CHOICE

    fun getValueAt(index: Int): String? {
        return if (values == null) null else if (index >= 0 && index < values.size) {
            values[index]
        } else {
            ""
        }
    }

    val selectedValue: String
        get() {
            if (setting == null) {
                return defaultValue ?: ""
            }

            return try {
                val stringSetting = setting as? AbstractStringSetting
                stringSetting?.string ?: defaultValue ?: ""
            } catch (_: ClassCastException) {
                try {
                    val shortSetting = setting as? AbstractShortSetting
                    shortSetting?.short?.toString() ?: defaultValue ?: ""
                } catch (_: ClassCastException) {
                    defaultValue ?: ""
                }
            }
        }

    val selectValueIndex: Int
        get() {
            val selectedValue = selectedValue
            return values?.indexOf(selectedValue) ?: -1
        }

    /**
     * Write a value to the backing int. If that int was previously null,
     * initializes a new one and returns it, so it can be added to the Hashmap.
     *
     * @param selection New value of the int.
     * @return the existing setting with the new value applied.
     */
    fun setSelectedValue(selection: String): AbstractStringSetting {
        val stringSetting = setting as? AbstractStringSetting
            ?: throw IllegalStateException("Setting is not an AbstractStringSetting")
        stringSetting.string = selection
        return stringSetting
    }

    fun setSelectedValue(selection: Short): AbstractShortSetting {
        val shortSetting = setting as? AbstractShortSetting
            ?: throw IllegalStateException("Setting is not an AbstractShortSetting")
        shortSetting.short = selection
        return shortSetting
    }
}
