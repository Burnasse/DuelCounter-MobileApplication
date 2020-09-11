package com.tlapp.duelcounter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = SliderFragment(position)
}