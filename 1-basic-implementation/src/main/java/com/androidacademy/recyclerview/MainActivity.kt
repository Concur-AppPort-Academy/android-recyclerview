package com.androidacademy.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Recycler View"

        setRecyclerView()
    }

    private fun generateRandomStrings(length: Int = 1, repeat: Int = 10) : List<String> {
        val strings: MutableList<String> = mutableListOf()
        val allowedChars = ('A'..'Z')

        repeat(repeat) {
            strings.add(
                (1..length)
                    .map { allowedChars.random() }
                    .joinToString("")
            )
        }
        return strings
    }

    private fun setRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ItemAdapter(generateRandomStrings())
        }
    }
}

class ItemAdapter(private val dataSet: List<String>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.line_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(position)
    override fun getItemCount(): Int = dataSet.size

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val textItem: TextView = view.findViewById(R.id.text_item)

        fun bind(position: Int) {
            textItem.text = dataSet[position]
        }
    }

}
