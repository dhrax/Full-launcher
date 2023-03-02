package com.daisa.launcher

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.daisa.launcher.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bottomSheetBehavior : BottomSheetBehavior<FrameLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeHome()
        initialize()
    }

    private fun initializeHome() {
        val colorList = arrayListOf("#00ff00", "#ff0000", "#0000ff", "#f0f0f0", "#00f0ff")
        val testScreenList = mutableListOf<HomeScreen>()
        for (i in 0..4) {
            val testScreenAppList = mutableListOf<AppInfo>()
            for (j in 0..4) {
                val appInfo = AppInfo().apply {
                    label = "app {$i}.{$j}"
                    packageName = ""
                    isSystemApp = false
                    icon = AppCompatResources.getDrawable(
                        this@MainActivity,
                        R.drawable.ic_launcher_foreground
                    )
                }
                testScreenAppList.add(appInfo)

            }
            val screen = HomeScreen(testScreenAppList)
            testScreenList.add(screen)
        }
        val homeScreenCollection = HomeScreenCollection(testScreenList)

        binding.pager.adapter = HomeScreenAdapter(this, colorList, homeScreenCollection)
    }

    private fun initialize() {

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet).apply {
            isHideable = true
            isDraggable = true
            peekHeight  = 0

            @SuppressLint("InternalInsetResource")
            val statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight = resources.getDimensionPixelSize(statusBarHeightId)
            maxHeight = ScreenMetricsCompat.getScreenSize(this@MainActivity).height - statusBarHeight
        }

        getAllApps(packageManager)

        binding.drawerGrid.adapter = AppDrawerAdapter(this, appsList)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    Log.d(TAG_DEBUG, "Estado cambiado: a hidden")

                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }else if (newState == BottomSheetBehavior.STATE_DRAGGING && binding.drawerGrid.getChildAt(0)?.y?.toInt() != 0)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    override fun onBackPressed() {

    }
}