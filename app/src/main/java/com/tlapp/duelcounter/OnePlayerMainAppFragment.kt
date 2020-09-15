package com.tlapp.duelcounter

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tlapp.duelcounter.extensions.*
import com.tlapp.duelcounter.list.HistoryView
import com.tlapp.duelcounter.listener.LifeGestureListener
import com.tlapp.duelcounter.listener.PreviewTextWatcher
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_oneplayer_mainapp.*
import kotlinx.android.synthetic.main.fragment_oneplayer_mainapp.coin_imageview
import kotlinx.android.synthetic.main.fragment_oneplayer_mainapp.lifePlayerOnePreview_textview
import kotlinx.android.synthetic.main.fragment_oneplayer_mainapp.playerOne_framelayout
import kotlinx.android.synthetic.main.fragment_oneplayer_mainapp.playerOne_textview
import kotlinx.android.synthetic.main.fragment_oneplayer_mainapp.timer_imagebutton
import kotlin.random.Random

class OnePlayerMainAppFragment : Fragment() {

    private val args: MainAppFragmentArgs by navArgs()

    private var isTimerOn = false
    private var history = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_oneplayer_mainapp, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyView = view.findViewById<ListView>(R.id.history_listview)
        historyView.visibility = View.INVISIBLE

        val counterView = HistoryView(history, historyView, context)

        val calculatorFragment =
            activity?.let { CalculatorFragment(counterView, it) }!!
        calculatorFragment.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.playerOne_textview).text = args.lifePoint.toString()

        val upRelativeLayout = view.findViewById<LinearLayout>(R.id.playerOneList_linearlayout)

        view.findViewById<ImageButton>(R.id.addCounterOne_button).setOnClickListener {
            val itemCounterPlayerOne = layoutInflater.inflate(
                R.layout.counter_view,
                upRelativeLayout,
                false
            )
            addCounter(upRelativeLayout, itemCounterPlayerOne)
        }

        view.findViewById<ImageButton>(R.id.timer_imagebutton).setOnClickListener {
            val chronometer = view.findViewById<Chronometer>(R.id.chronometer_view)
            if (!isTimerOn) {
                timer_imagebutton.setImageResource(R.drawable.ic_timer_on)
                chronometer.base = SystemClock.elapsedRealtime()
                chronometer.start()
                isTimerOn = true
            } else {
                timer_imagebutton.setImageResource(R.drawable.ic_timer_off)
                chronometer.stop()
                isTimerOn = false
            }
        }

        view.findViewById<ImageButton>(R.id.playerOneCalcul_button).setOnClickListener {
            switchView(
                playerOne_textview.text.toString().toInt(),
                true,
                view,
                calculatorFragment,
                calculator_layout
            )
        }

        view.findViewById<ImageButton>(R.id.reset_imagebutton).setOnClickListener {
            historyView.visibility = AdapterView.INVISIBLE
            history.clear()
            playerOne_textview.text = args.lifePoint.toString()
            view.findViewById<Chronometer>(R.id.chronometer_view).stop()
            timer_imagebutton.setImageResource(R.drawable.ic_timer_off)
            isTimerOn = false
        }

        view.findViewById<ImageButton>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_onePlayerMainAppFragment_to_MainMenuFragment)
        }

        view.findViewById<ImageButton>(R.id.tosscoin_imagebutton).setOnClickListener {
            val tossCoin = Random.nextBoolean()
            if (tossCoin)
                coin_imageview.setImageResource(R.drawable.coinpile)
            else
                coin_imageview.setImageResource(R.drawable.coinface)

            circularReveal(coin_imageview)

            val handler = Handler()
            handler.postDelayed({ circularHide(coin_imageview) }, 2000)

        }

        view.findViewById<ImageButton>(R.id.history_button).setOnClickListener {
            if (history.isNotEmpty()) {
                if (historyView.visibility == View.INVISIBLE)
                    circularReveal(historyView)
                else
                    circularHide(historyView)
            }
        }

        historyView.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            circularHide(historyView)
        }

        val playerOnePreviewTextWatcher = PreviewTextWatcher(lifePlayerOnePreview_textview)

        lifePlayerOnePreview_textview.addTextChangedListener(playerOnePreviewTextWatcher)

        val gestureDetectorPlayerOne = GestureDetector(
            context,
            LifeGestureListener(
                lifePoint = playerOne_textview,
                preview = lifePlayerOnePreview_textview,
                playerText = playerOne_edittext,
                historyView = counterView,
                activity = requireActivity()
            )
        )

        val touchListenerPlayerOne: View.OnTouchListener = View.OnTouchListener { v, event ->
            v.performClick()
            gestureDetectorPlayerOne.onTouchEvent(event)
        }

        playerOne_framelayout.setOnTouchListener(touchListenerPlayerOne)
    }

}