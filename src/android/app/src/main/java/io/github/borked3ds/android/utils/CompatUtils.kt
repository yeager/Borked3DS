// Copyright 2024 Mandarine Project
// Copyright 2025 Borked3DS Project
// Licensed under GPLv2 or any later version
// Refer to the license.txt file included.

package io.github.borked3ds.android.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

object CompatUtils {
    fun findActivity(context: Context): Activity {
        return when (context) {
            is Activity -> context
            is ContextWrapper -> findActivity(context.baseContext)
            else -> throw IllegalArgumentException("Context is not an Activity")
        }
    }
}
