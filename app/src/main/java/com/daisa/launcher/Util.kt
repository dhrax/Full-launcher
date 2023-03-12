package com.daisa.launcher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.annotation.RequiresApi


val TAG_DEBUG = "DAVID"
val NUM_PAGES = 4

val displayMetrics = DisplayMetrics()
/* Objects used by multiple activities */
val appsList: MutableList<AppInfo> = ArrayList()

fun getAllApps(packageManager: PackageManager){
    val appInfoList = mutableListOf<AppInfo>()

    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)

    val allApps = packageManager.queryIntentActivities(intent, 0)

    for(app in allApps){
        val appInfo = AppInfo()
        appInfo.label = app.loadLabel(packageManager)
        appInfo.icon = app.loadIcon(packageManager)
        appInfo.packageName = app.activityInfo.packageName
        appInfoList.add(appInfo)
    }
    appInfoList.sortBy { it.label.toString().lowercase() }

    appsList.clear()
    appsList.addAll(appInfoList)
}

object ScreenMetricsCompat {
    private val api: Api =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ApiLevel30()
        else Api()

    /**
     * Returns screen size in pixels.
     */
    fun getScreenSize(context: Context): Size = api.getScreenSize(context)

    @Suppress("DEPRECATION")
    private open class Api {
        open fun getScreenSize(context: Context): Size {
            val display = context.getSystemService(WindowManager::class.java).defaultDisplay
            val metrics = if (display != null) {
                DisplayMetrics().also { display.getRealMetrics(it) }
            } else {
                Resources.getSystem().displayMetrics
            }
            return Size(metrics.widthPixels, metrics.heightPixels)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private class ApiLevel30 : Api() {
        override fun getScreenSize(context: Context): Size {
            val metrics: WindowMetrics = context.getSystemService(WindowManager::class.java).currentWindowMetrics
            return Size(metrics.bounds.width(), metrics.bounds.height())
        }
    }
}


fun findAppByPackageName(packageName: String): AppInfo? {
    return appsList.firstOrNull { app -> app.packageName.equals(packageName) }
}
