package com.trinitydigital.network.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.trinitydigital.network.ui.MainActivity

object PermissionUtils {

    const val LOCATION_REQUEST_CODE = 111

    private val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun checkLocationPermission(activity: AppCompatActivity): Boolean {
        return if (isCheckedPermission(activity))
            true
        else {
            ActivityCompat.requestPermissions(activity, permission, LOCATION_REQUEST_CODE)
            false
        }
    }


    private fun isCheckedPermission(context: Context): Boolean {
        val permissionsFine =
            ContextCompat.checkSelfPermission(
                context,
                permission[0]
            ) == PackageManager.PERMISSION_GRANTED

        val permissionsCoarse =
            ContextCompat.checkSelfPermission(
                context,
                permission[1]
            ) == PackageManager.PERMISSION_GRANTED

        return permissionsFine && permissionsCoarse
    }
}