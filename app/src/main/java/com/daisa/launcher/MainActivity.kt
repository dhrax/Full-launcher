package com.daisa.launcher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.daisa.launcher.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet).apply {
            isHideable = false
            peekHeight = 300
        }

        getAllApps(packageManager)

        binding.drawerGrid.adapter = AppDrawerAdapter(this, appsList)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                val stateHiddenOrDragging =
                    newState == BottomSheetBehavior.STATE_HIDDEN || newState == BottomSheetBehavior.STATE_DRAGGING

                if (stateHiddenOrDragging && binding.drawerGrid.getChildAt(0).y.toInt() != 0)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    override fun onBackPressed() {

    }
}