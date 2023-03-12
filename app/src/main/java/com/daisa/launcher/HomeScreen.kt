package com.daisa.launcher

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.daisa.launcher.databinding.FragmentHomeScreenBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class HomeScreen(
    private val appsOnScreen: MutableList<AppInfo>
) : Fragment(), View.OnTouchListener, View.OnDragListener {

    private lateinit var binding: FragmentHomeScreenBinding

    private var lastPosPressedY = 0f
    private var lastTouchPosY = 0f
    private lateinit var mainActivity: MainActivity

    private lateinit var adapter: AppDrawerAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)

        mainActivity = activity as MainActivity

        binding.root.setOnTouchListener(this)
        binding.root.setOnDragListener(this)

        adapter = AppDrawerAdapter(this.requireContext(), appsOnScreen)

        binding.homeScreenGrid.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    private fun setupData() {
        val color = arguments?.getString("color")
        binding.root.setBackgroundColor(Color.parseColor(color))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastPosPressedY = event.rawY
                return true
            }
            MotionEvent.ACTION_UP -> {
                getGesture(lastTouchPosY, lastPosPressedY)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                lastTouchPosY = event.rawY

            }
            MotionEvent.ACTION_CANCEL -> {
                getGesture(lastTouchPosY, lastPosPressedY)
            }
        }
        return false
    }

    private fun getGesture(lastTouchPosY: Float, lastPosPressedY: Float) {
        val dy = lastTouchPosY - lastPosPressedY

        val isBigEnough = dy > 500 || dy < -500 //export limit
        if (isBigEnough) {
            if (dy < 0) {
                Log.d(TAG_DEBUG, "abrir appdrawer")

                mainActivity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            } else {
                Log.d(TAG_DEBUG, "hacia abajo")
            }
        }
    }

    override fun onDrag(view: View, dragEvent: DragEvent): Boolean {

        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                mainActivity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                view.animate().start()

            }
            DragEvent.ACTION_DRAG_ENDED, DragEvent.ACTION_DRAG_EXITED -> {
                view.animate().cancel()
            }
            DragEvent.ACTION_DROP -> {
                val app = findAppByPackageName(dragEvent.clipData.getItemAt(0).text as String)

                if (app != null) {
                    appsOnScreen.add(app)
                    adapter.updateAdapter(appsOnScreen)
                } else {
                    Log.e(
                        TAG_DEBUG,
                        "No se ha podido añadir la app seleccionada a la pantalla de inicio"
                    )
                }
            }
        }
        return true
    }

}