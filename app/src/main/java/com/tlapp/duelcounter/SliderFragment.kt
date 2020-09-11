package com.tlapp.duelcounter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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

        title_textview.text = resources.getText(titleArray[position])

        view.findViewById<Button>(R.id.classic_button).setOnClickListener {
            findNavController().navigate(navigationClassicArray[position])
        }

        view.findViewById<Button>(R.id.speedDuel_button).setOnClickListener {
            findNavController().navigate(navigationSpeedArray[position])
        }

        if (resources.getText(titleArray[position]) == context?.getText(R.string.fourPlayer_button_text))
            view.findViewById<Button>(R.id.speedDuel_button).visibility = View.INVISIBLE

    }

}