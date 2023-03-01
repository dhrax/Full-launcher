package com.daisa.launcher

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeScreenAdapter(
    fa: FragmentActivity, private val colorList: ArrayList<String>,
    private val homeScreenCollection: HomeScreenCollection
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = homeScreenCollection.homeScreenList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = homeScreenCollection.homeScreenList[position]
        val args = Bundle()
        args.putString("color", colorList[position])
        fragment.arguments = args
        return fragment
    }
}