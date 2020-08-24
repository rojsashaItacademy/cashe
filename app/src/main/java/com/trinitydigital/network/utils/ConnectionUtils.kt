package com.trinitydigital.network.utils

import android.content.Context
import android.net.ConnectivityManager

object ConnectionUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val one = cm.activeNetworkInfo != null
        val two = cm.activeNetworkInfo?.isConnected ?: false

        return one && two
    }
}