package com.tlapp.duelcounter

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.*
import android.widget.*
import android.widget.AdapterView.INVISIBLE
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tlapp.duelcounter.extensions.addCounter
import com.tlapp.duelcounter.extensions.circularHide
import com.tlapp.duelcounter.extensions.circularReveal
import com.tlapp.duelcounter.extensions.switchView
import com.tlapp.duelcounter.list.HistoryView
import com.tlapp.duelcounter.listener.GestureListener
import com.tlapp.duelcounter.listener.PreviewTextWatcher
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_twoplayer_mainapp.*
import kotlinx.android.synthetic.main.fragment_twoplayer_mainapp.coin_imageview
import kotlinx.android.synthetic.main.fragment_twoplayer_mainapp.lifePlayerOnePreview_textview
import kotlinx.android.synthetic.main.fragment_twoplayer_mainapp.playerOne_edittext
import kotlinx.android.synthetic.main.fragment_twoplayer_mainapp.playerOne_framelayout
import kotlinx.android.synthetic.main.fragment_twoplayer_mainapp.playerOne_textview
import kotlinx.android.synthetic.main.fragment_twoplayer_mainapp.timer_imagebutton
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MainAppFragment : Fragment() {

    private val args: MainAppFragmentArgs by navArgs()

    private var isTimerOn = false
    private var history = ArrayList<String>()

    private var playerOneDefaultLife = 0
    private var playerTwoDefaultLife = 0

    private var isCalculatorRotate = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_twoplayer_mainapp, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            playerOneDefaultLife = args.lifePoint
            playerTwoDefaultLife = args.lifePoint
            super.onCreate(savedInstanceState)
            return
        }
        history = savedInstanceState.getStringArrayList("History") as ArrayList<String>
        playerOneDefaultLife = savedInstanceState.getInt("PlayerOneLifePoint")
        playerTwoDefaultLife = savedInstanceState.getInt("PlayerTwoLifePoint")
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList("History", history)
        outState.putInt(
            "PlayerOneLifePoint",
            if (view?.findViewById<TextView>(R.id.playerOne_textview)?.text != null) view?.findViewById<TextView>(
                R.id.playerOne_textview
            )?.text.toString().toInt() else args.lifePoint
        )
        outState.putInt(
            "PlayerTwoLifePoint",
            if (view?.findViewById<TextView>(R.id.playerTwo_textview)?.text != null) view?.findViewById<TextView>(
                R.id.playerTwo_textview
            )?.text.toString().toInt() else args.lifePoint
        )
        super.onSaveInstanceState(outState)
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

        view.findViewById<TextView>(R.id.playerOne_textview).text = playerOneDefaultLife.toString()
        view.findViewById<TextView>(R.id.playerTwo_textview).text = playerTwoDefaultLife.toString()

        val downRelativeLayout = view.findViewById<LinearLayout>(R.id.playerTwoList_linearlayout)
        val upRelativeLayout = view.findViewById<LinearLayout>(R.id.playerOneList_linearlayout)

        view.findViewById<ImageButton>(R.id.addCounterTwo_button).setOnClickListener {
            val itemCounterPlayerTwo = layoutInflater.inflate(
                R.layout.counter_view,
                downRelativeLayout,
                false
            )
            addCounter(downRelativeLayout, itemCounterPlayerTwo)
        }

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
            if(!isCalculatorRotate) calculator_layout.rotation = 180F; isCalculatorRotate = true
            switchView(
                playerOne_textview.text.toString().toInt(),
                true,
                view,
                calculatorFragment,
                calculator_layout
            )
        }

        view.findViewById<ImageButton>(R.id.playerTwoCalcul_button).setOnClickListener {
            if(isCalculatorRotate) calculator_layout.rotation = 0F; isCalculatorRotate = false
            switchView(
                playerTwo_textview.text.toString().toInt(),
                false,
                view,
                calculatorFragment,
                calculator_layout
            )
        }

        view.findViewById<ImageButton>(R.id.reset_imagebutton).setOnClickListener {
            historyView.visibility = INVISIBLE
            history.clear()
            playerOne_textview.text = args.lifePoint.toString()
            playerTwo_textview.text = args.lifePoint.toString()
            view.findViewById<Chronometer>(R.id.chronometer_view).stop()
            timer_imagebutton.setImageResource(R.drawable.ic_timer_off)
            isTimerOn = false
        }

        view.findViewById<ImageButton>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        view.findViewById<ImageButton>(R.id.tosscoin_imagebutton).setOnClickListener {
            val tossCoin = Random.nextBoolean()
            if (tossCoin)
                coin_imageview.setImageResource(R.drawable.coinpile)
            else
                coin_imageview.setImageResource(R.drawable.coinface)

            circularReveal(coin_imageview)

            val handler = Handler()
            handler.postDelayed({ coin_imageview.visibility = View.INVISIBLE }, 2000)

        }

        view.findViewById<ImageButton>(R.id.history_button).setOnClickListener {
            if (history.isNotEmpty()) {
                if (historyView.visibility == View.INVISIBLE)
                    circularReveal(historyView)
                else
                    circularHide(historyView)
            }
        }

        historyView.onItemClickListener = OnItemClickListener { _, _, _, _ ->
            circularHide(historyView)
        }

        val playerOnePreviewTextWatcher = PreviewTextWatcher(lifePlayerOnePreview_textview)
        val playerTwoPreviewTextWatcher = PreviewTextWatcher(lifePlayerTwoPreview_textview)

        lifePlayerOnePreview_textview.addTextChangedListener(playerOnePreviewTextWatcher)
        lifePlayerTwoPreview_textview.addTextChangedListener(playerTwoPreviewTextWatcher)

        val gestureDetectorPlayerOne = GestureDetector(
            context,
            GestureListener(
                lifePoint = playerOne_textview,
                preview = lifePlayerOnePreview_textview,
                playerText = playerOne_edittext,
                historyView = counterView,
                activity = requireActivity()
            )
        )
        val gestureDetectorPlayerTwo = GestureDetector(
            context,
            GestureListener(
                lifePoint = playerTwo_textview,
                preview = lifePlayerTwoPreview_textview,
                playerText = playerTwo_edittext,
                historyView = counterView,
                activity = requireActivity()
            )
        )

        val touchListenerPlayerOne: View.OnTouchListener = View.OnTouchListener { v, event ->
            v.performClick()
            gestureDetectorPlayerOne.onTouchEvent(event)
        }

        val touchListenerPlayerTwo: View.OnTouchListener = View.OnTouchListener { v, event ->
            v.performClick()
            gestureDetectorPlayerTwo.onTouchEvent(event)
        }

        playerOne_framelayout.setOnTouchListener(touchListenerPlayerOne)
        playerTwo_framelayout.setOnTouchListener(touchListenerPlayerTwo)
    }

}
