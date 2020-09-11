package com.tlapp.duelcounter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.tlapp.duelcounter.extensions.circularHide
import com.tlapp.duelcounter.extensions.countDownAnimation
import com.tlapp.duelcounter.list.HistoryView

class CalculatorFragment(var historyView: HistoryView, private val activity: Activity) :
    Fragment() {

    var lifePoint = 1
    var secondMember = ""
    var result = 1
    var currentOperator = ""
    var isPlayerOne = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calculatorView = view.findViewById<ConstraintLayout>(R.id.calculator_layout)

        result = lifePoint

        val calculTextview = view.findViewById<TextView>(R.id.calculator_textview)
        val resultTextView = view.findViewById<TextView>(R.id.result_textview)

        calculTextview.text = lifePoint.toString()
        resultTextView.text = lifePoint.toString()

        view.findViewById<Button>(R.id.zero_buton).setOnClickListener {
            writeCalcul(calculTextview, "0")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.one_button).setOnClickListener {
            writeCalcul(calculTextview, "1")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.two_button).setOnClickListener {
            writeCalcul(calculTextview, "2")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.three_button).setOnClickListener {
            writeCalcul(calculTextview, "3")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.four_button).setOnClickListener {
            writeCalcul(calculTextview, "4")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.five_button).setOnClickListener {
            writeCalcul(calculTextview, "5")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.six_button).setOnClickListener {
            writeCalcul(calculTextview, "6")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.seven_button).setOnClickListener {
            writeCalcul(calculTextview, "7")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.eigth_button).setOnClickListener {
            writeCalcul(calculTextview, "8")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.nine_button).setOnClickListener {
            writeCalcul(calculTextview, "9")
            resultTextView.text = result.toString()
        }

        view.findViewById<Button>(R.id.add_button).setOnClickListener {
            writeOperator(calculTextview, "+")

        }

        view.findViewById<Button>(R.id.sub_button).setOnClickListener {
            writeOperator(calculTextview, "-")
        }

        view.findViewById<Button>(R.id.mult_button).setOnClickListener {
            writeOperator(calculTextview, "*")
        }

        view.findViewById<Button>(R.id.div_button).setOnClickListener {
            writeOperator(calculTextview, "/")
        }

        view.findViewById<Button>(R.id.erase_button).setOnClickListener {
            lifePoint = if (isPlayerOne)
                view.findViewById<TextView>(R.id.playerOne_textview).text.toString().toInt()
            else
                view.findViewById<TextView>(R.id.playerTwo_textview).text.toString().toInt()
            resetValue()
            calculTextview.text = lifePoint.toString()
            resultTextView.text = lifePoint.toString()
        }

        view.findViewById<Button>(R.id.backspace_button).setOnClickListener {
            if (secondMember.isNotEmpty()) {
                secondMember = secondMember.substring(0, secondMember.length - 1)
                val newString = calculTextview.text.substring(0, calculTextview.text.length - 1)
                calculTextview.text = newString

                if (secondMember.isEmpty())
                    resultTextView.text = lifePoint.toString()
                else {
                    calculate()
                    resultTextView.text = result.toString()
                }
            }
        }

        view.findViewById<Button>(R.id.apply_button).setOnClickListener {

            circularHide(calculatorView)
            if (isPlayerOne) {
                val playerName = view.findViewById<EditText>(R.id.playerOne_edittext).text
                if (lifePoint < result)
                    historyView.addItems("$playerName " + activity.getString(R.string.gain_text) + " ${result - lifePoint}")
                else
                    historyView.addItems("$playerName " + activity.getString(R.string.loose_text) + " ${lifePoint - result}")
                countDownAnimation(
                    result,
                    view.findViewById<TextView>(R.id.playerOne_textview),
                    activity
                )
            } else {
                val playerName = view.findViewById<EditText>(R.id.playerTwo_edittext).text
                if (lifePoint < result)
                    historyView.addItems("$playerName " + activity.getString(R.string.gain_text) + " ${result - lifePoint}")
                else
                    historyView.addItems("$playerName " + activity.getString(R.string.loose_text) + " ${lifePoint - result}")
                countDownAnimation(
                    result,
                    view.findViewById<TextView>(R.id.playerTwo_textview),
                    activity
                )
            }
        }

        view.findViewById<Button>(R.id.back_button).setOnClickListener {
            circularHide(calculatorView)
        }

    }

    private val addition = { x: Int, y: Int -> x + y }
    private val subtraction = { x: Int, y: Int -> x - y }
    private val multiplication = { x: Int, y: Int -> x * y }
    private val division = { x: Int, y: Int -> x / y }
    private inline fun executeOperation(x: Int, y: Int, operation: (Int, Int) -> Int) = operation(
        x,
        y
    )

    private fun calculate() {
        when (currentOperator) {
            "+" -> result = executeOperation(lifePoint, secondMember.toInt(), addition)
            "-" -> result = executeOperation(lifePoint, secondMember.toInt(), subtraction)
            "*" -> result = executeOperation(lifePoint, secondMember.toInt(), multiplication)
            "/" -> result = executeOperation(lifePoint, secondMember.toInt(), division)
        }
    }

    private fun writeCalcul(textView: TextView, stringNumber: String) {
        if (currentOperator.isNotEmpty()) {
            val newString = textView.text.toString() + stringNumber
            textView.text = newString
            secondMember += stringNumber
            calculate()
        }
    }

    private fun writeOperator(textView: TextView, operator: String) {
        if (secondMember.isNotEmpty() || currentOperator.isEmpty()) {
            currentOperator = operator
            val newString = textView.text.toString() + operator
            textView.text = newString
            lifePoint = result
            secondMember = ""
        }
    }

    private fun resetValue() {
        secondMember = ""
        result = lifePoint
        currentOperator = ""
    }

    fun initValue(lifePoint: Int, isPlayerOne: Boolean, view: View) {
        this.lifePoint = lifePoint
        this.isPlayerOne = isPlayerOne
        result = lifePoint
        secondMember = ""
        currentOperator = ""
        view.findViewById<TextView>(R.id.calculator_textview).text = lifePoint.toString()
        view.findViewById<TextView>(R.id.result_textview).text = lifePoint.toString()
    }

}