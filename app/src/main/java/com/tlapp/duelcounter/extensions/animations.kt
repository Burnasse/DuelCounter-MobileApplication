package com.tlapp.duelcounter.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.TextView
import com.tlapp.duelcounter.R
import kotlin.math.hypot

fun countDownAnimation(endCount: Int, textView: TextView, activity: Activity) {
    val handler = Handler()
    var textInt = textView.text.toString().toInt()

    val life: Int
    val finLife: Int

    val soundPool: SoundPool

    soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build()
    } else {
        SoundPool(1, AudioManager.FLAG_PLAY_SOUND, 0)
    }

    life = soundPool.load(activity, R.raw.life2, 1)
    finLife = soundPool.load(activity, R.raw.finlife, 1)

    fun countDown(newValue: Int, textView: TextView): Runnable {
        return Runnable {
            textView.text = newValue.toString()
        }
    }

    Thread {
        soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
            soundPool.play(life, 1F, 1F, 1, -1, 1f)
        }

        while (endCount != textInt) {
            if (endCount > textInt) textInt++
            else textInt--
            handler.postDelayed(countDown(textInt, textView), 1)
            Thread.sleep(0, 500000)
        }
        soundPool.pause(life)
        soundPool.unload(life)
        soundPool.play(finLife, 1f, 1f, 1, 0, 1f)
    }.start()

}

fun crossfade(view: View) {
    view.apply {
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        alpha = 0f
        visibility = View.VISIBLE

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        animate()
            .alpha(1f)
            .setDuration(250)
            .setListener(null)

    }

}

fun circularReveal(view: View) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        val cx = view.width / 2
        val cy = view.height / 2
        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)

        view.visibility = View.VISIBLE
        anim.start()
    } else {
        view.visibility = View.VISIBLE
    }

}

fun circularHide(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val cx = view.width / 2
        val cy = view.height / 2

        val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0f)

        anim.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.visibility = View.INVISIBLE
            }
        })

        anim.start()
    } else {
        view.visibility = View.INVISIBLE
    }

}