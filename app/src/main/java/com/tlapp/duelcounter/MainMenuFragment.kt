package com.tlapp.duelcounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_mainmenu.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mainmenu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topLayout = topSlide_imageview
        val bottomLayout = bottomSlide_imageview

        Glide.with(this).load(R.drawable.smallyugi).into(topLayout)

        Glide.with(this).load(R.drawable.smallmagician2).into(bottomLayout)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        slide_viewpager.adapter = pagerAdapter

        TabLayoutMediator(tabDots, slide_viewpager) { _, _ -> }.attach()

    }

}
