package com.daisa.launcher

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daisa.launcher.databinding.FragmentHomeScreenBinding


class HomeScreen(
    private val appsOnScreen: MutableList<AppInfo>
) : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)

        binding.homeScreenGrid.adapter = AppDrawerAdapter(this.requireContext(), appsOnScreen)

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


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.appItemLayout -> {
                Log.d(TAG_DEBUG, "App pulsada")
            }
        }
    }
    /*
    fun createIcon(inflater: LayoutInflater, container: ViewGroup?){
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home_screen, container, false)
        val icon: ImageView = v.findViewById<ImageView?>(R.id.icon).apply {
            setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_launcher_foreground))
        }
        icon.setOnClickListener(this)
    }

     */
}