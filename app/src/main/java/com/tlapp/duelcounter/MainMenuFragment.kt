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

        Glide.with(this).load(R.drawable.yugi).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                topLayout.setImageDrawable(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

        Glide.with(this).load(R.drawable.magician2).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                bottomLayout.setImageDrawable(resource)
                val matrix = bottomLayout.imageMatrix
                val scale: Float
                val viewWidth = bottomLayout.width - bottomLayout.paddingLeft - bottomLayout.paddingRight
                val viewHeight = bottomLayout.height - bottomLayout.paddingTop - bottomLayout.paddingBottom
                val drawableWidth = bottomLayout.drawable.intrinsicWidth
                val drawableHeight = bottomLayout.drawable.intrinsicHeight
                scale = if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
                    viewHeight.toFloat() / drawableHeight.toFloat()
                } else {
                    viewWidth.toFloat() / drawableWidth.toFloat()
                }
                matrix.setScale(scale, scale)
                bottomLayout.imageMatrix = matrix
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        slide_viewpager.adapter = pagerAdapter

        TabLayoutMediator(tabDots, slide_viewpager) { _, _ -> }.attach()

    }

}
