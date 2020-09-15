package com.tlapp.duelcounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.slide_layout.*

class SliderFragment(private val position: Int = 0) : Fragment() {

    private val titleArray = listOf(R.string.singlePlayer_button_text,R.string.twoPlayer_button_text,R.string.fourPlayer_button_text)

    private val navigationClassicArray = listOf(
        MainMenuFragmentDirections.actionMainMenuFragmentToOnePlayerMainAppFragment(),
        MainMenuFragmentDirections.actionFirstFragmentToSecondFragment(),
        MainMenuFragmentDirections.actionFirstFragmentToSecondFragment(16000)
    )

    private val navigationSpeedArray = listOf(
        MainMenuFragmentDirections.actionMainMenuFragmentToOnePlayerMainAppFragment(4000),
        MainMenuFragmentDirections.actionFirstFragmentToSecondFragment(4000),
        MainMenuFragmentDirections.actionFirstFragmentToSecondFragment(16000)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.slide_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        slideText_textview.text = resources.getText(titleArray[position])

        upperSlide_framelayout.setOnClickListener {
            findNavController().navigate(navigationClassicArray[position])
        }

        downSlide_framelayout.setOnClickListener {
            findNavController().navigate(navigationSpeedArray[position])
        }

    }

}