package com.tlapp.duelcounter.extensions

import android.view.View
import android.view.animation.Animation
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewSwitcher
import com.tlapp.duelcounter.CalculatorFragment
import com.tlapp.duelcounter.R

fun addCounter(linearLayout: LinearLayout, viewToAdd: View) {

    viewToAdd.findViewById<ImageButton>(R.id.increment_imagebutton).setOnClickListener {
        val v = viewToAdd.findViewById<TextView>(R.id.counter_textview)
        val a = v.text.toString().toInt() + 1
        v.text = a.toString()
    }

    viewToAdd.findViewById<ImageButton>(R.id.decrement_imagebutton).setOnClickListener {
        val v = viewToAdd.findViewById<TextView>(R.id.counter_textview)
        val a = v.text.toString().toInt() - 1
        v.text = a.toString()
    }

    viewToAdd.findViewById<ImageButton>(R.id.delete_imagebutton).setOnClickListener {
        linearLayout.removeView(viewToAdd)
    }

    linearLayout.addView(viewToAdd)
}

fun switchView(
    lifePoint: Int,
    isPlayerOne: Boolean,
    view: View,
    calculatorFragment: CalculatorFragment,
    switchView: View
) {
    calculatorFragment.initValue(lifePoint, isPlayerOne, view)
    circularReveal(switchView)
}