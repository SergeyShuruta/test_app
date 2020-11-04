package com.sergey.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 5, GridLayoutManager.HORIZONTAL, false)
        val adapter = TestAdapter(mutableListOf<String>().apply {
            for (i in 1..500) {
                add("Item $i")
            }
        })
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(TestDecoration(recyclerView, adapter))
    }
}