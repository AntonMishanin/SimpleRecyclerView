package com.paint.simplerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.paint.simplerecyclerview.data.Repository
import com.paint.simplerecyclerview.data.local.LocalDataSource
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val adapter = MyAdapter()

    private val repository = Repository(LocalDataSource())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val add = findViewById<Button>(R.id.add)
        add.setOnClickListener {
            repository.add(ItemUi(UUID.randomUUID().toString()))
            adapter.submitList(repository.getAll())
        }

        val listRecyclerView = findViewById<RecyclerView>(R.id.list)
        listRecyclerView.adapter = adapter
    }
}

