package com.tlapp.duelcounter.list

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListView
import com.tlapp.duelcounter.R

class HistoryView(var history: ArrayList<String>, listView: ListView, context: Context?) {

    var arrayAdapter: ArrayAdapter<String> =
        context?.let { ArrayAdapter(it, R.layout.list_item, R.id.textItem, history) }!!

    init {
        listView.adapter = arrayAdapter
    }

    fun addItems(s: String) {
        history.add(s)
        arrayAdapter.notifyDataSetChanged()
    }
}