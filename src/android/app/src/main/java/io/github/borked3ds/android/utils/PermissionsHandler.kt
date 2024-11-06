// Copyright 2023 Citra Emulator Project
// Copyright 2024 Borked3DS Emulator Project
// Licensed under GPLv2 or any later version
// Refer to the license.txt file included.

package io.github.borked3ds.android.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.preference.PreferenceManager
import androidx.documentfile.provider.DocumentFile
import io.github.borked3ds.android.Borked3DSApplication

object PermissionsHandler {
    const val BORKED3DS_DIRECTORY = "BORKED3DS_DIRECTORY"
    val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(Borked3DSApplication.appContext)

    fun hasWriteAccess(context: Context): Boolean {
        try {
            if (borked3dsDirectory.toString().isEmpty()) {
                return false
            }

            val uri = borked3dsDirectory
            val takeFlags =
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, takeFlags)
            val root = DocumentFile.fromTreeUri(context, uri)
            if (root != null && root.exists()) {
                return true
            }

            context.contentResolver.releasePersistableUriPermission(uri, takeFlags)
        } catch (e: Exception) {
            Log.error("[PermissionsHandler]: Cannot check borked3ds data directory permission, error: " + e.message)
        }
        return false
    }

    val borked3dsDirectory: Uri
        get() {
            val directoryString = preferences.getString(BORKED3DS_DIRECTORY, "")
            return Uri.parse(directoryString)
        }

    fun setBorked3DSDirectory(uriString: String?) =
        preferences.edit().putString(BORKED3DS_DIRECTORY, uriString).apply()
}
