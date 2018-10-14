package com.example.tvmaze.management

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object PermissionManager {
    private const val write_external_storage = "android.permission.WRITE_EXTERNAL_STORAGE"

    private val permissions = arrayOf(write_external_storage)

    private fun askPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(activity, permissions, 1)
    }

    private fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun checkAndAskPermissions(activity: Activity) {
        permissions
                .asSequence()
                .filterNot { checkPermission(activity, it) }
                .forEach { _ -> askPermissions(activity) }
    }

    fun checkPermissions(activity: Activity): Boolean {
        return permissions.any { checkPermission(activity, it) }
    }

}
