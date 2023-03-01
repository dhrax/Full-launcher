package com.daisa.launcher

import android.content.Intent
import android.content.pm.PackageManager
import android.util.DisplayMetrics


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