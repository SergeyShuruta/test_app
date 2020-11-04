package com.sergey.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TestAdapter(
        private val items: List<String>
): RecyclerView.Adapter<TestAdapter.TestViewHolder>(), TestDecoration.TestHeaderInterface {

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val testTextView: TextView = itemView.findViewById(R.id.testTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false))
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.testTextView.text = items[position]
        holder.testTextView.setOnClickListener {
            Log.d("TEST", "-Click: $position")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun bindHeaderData(header: View, position: Int) {
        header.findViewById<TextView>(R.id.testTextView).text = items[position]
        header.findViewById<TextView>(R.id.testTextView).setOnClickListener {
            Log.d("TEST", "Click: $position")
        }
    }

    private fun isHeader(position: Int) = position < 5

}