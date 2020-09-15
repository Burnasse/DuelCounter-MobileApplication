package com.tlapp.duelcounter.listener

import android.app.Activity
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.tlapp.duelcounter.R
import com.tlapp.duelcounter.extensions.countDownAnimation
import com.tlapp.duelcounter.list.HistoryView

class LifeGestureListener(private val lifePoint: TextView,
                          private val preview: TextView,
                          private val historyView: HistoryView,
                          private val playerText: EditText,
                          private val activity: Activity) : GestureDetector.SimpleOnGestureListener() {

    private var currentPreviewValue = preview.text.toString().toInt()

    override fun onDown(event: MotionEvent?): Boolean {
        currentPreviewValue = preview.text.toString().toInt()
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        if(preview.visibility == View.INVISIBLE) return true
        val playerName = playerText.text
        val currentLifePoint = lifePoint.text.toString().toInt()
        val result = currentLifePoint+preview.text.toString().toInt()
        if(currentPreviewValue >0)
            historyView.addItems("$playerName "+ activity.getString(R.string.gain_text) +" ${result-currentLifePoint}")
        else
            historyView.addItems("$playerName "+ activity.getString(R.string.loose_text) +" ${currentLifePoint-result}")
        countDownAnimation(
            result,
            lifePoint,
            activity
        )
        preview.text = "0"
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?, e2: MotionEvent?,
        distanceX: Float, distanceY: Float
    ): Boolean {
        val distance = ((e1!!.y) - (e2!!.y)).toInt()/100
        val newText = (currentPreviewValue + distance*50).toString()
        preview.text = newText

        return true
    }

    override fun onFling(
        event1: MotionEvent?, event2: MotionEvent?,
        velocityX: Float, velocityY: Float
    ): Boolean {
        return true
    }



}
