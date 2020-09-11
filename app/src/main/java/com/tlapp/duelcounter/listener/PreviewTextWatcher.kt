package com.tlapp.duelcounter.listener

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView

class PreviewTextWatcher(val textView: TextView) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        textView.visibility = if (textView.text.toString().toInt() != 0)
            View.VISIBLE
        else
            View.INVISIBLE
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}