package com.tlapp.duelcounter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_mainmenu.*
import kotlinx.android.synthetic.main.slide_layout.*

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

        val layout = slideBackground

        Glide.with(this).load(R.drawable.yugi).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                layout.setImageDrawable(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        slide_viewpager.adapter = pagerAdapter

        TabLayoutMediator(tabDots, slide_viewpager) { _, _ -> }.attach()

    }

}
