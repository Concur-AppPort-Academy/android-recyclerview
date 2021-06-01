package com.androidacademy.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

    private fun generateRandomStrings(length: Int = 1, repeat: Int = 10) : MutableList<String> {
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
            adapter = ItemAdapter(generateRandomStrings()){ position->
                onItemClicked(position)
            }
        }

        val button = findViewById<Button>(R.id.addButton)
        button.setOnClickListener{
            val adapter = recyclerView.adapter as ItemAdapter
            adapter.addItem("Random")
            adapter.notifyDataSetChanged()
        }
    }

    private fun onItemClicked(item: Int){
        Toast.makeText(this, "Item Clicked $item", Toast.LENGTH_SHORT).show()
    }
}

class ItemAdapter(private val dataSet: MutableList<String>, private val onItemClicked: (Int) -> Unit): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.line_item, parent, false)
        return ItemViewHolder(view, onItemClicked = onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(position)
    override fun getItemCount(): Int = dataSet.size

    fun addItem(value: String, position: Int = 0){
        dataSet.add(position, value)
    }

    inner class ItemViewHolder(view: View, val onItemClicked: (Int) -> Unit): RecyclerView.ViewHolder(view) {
        private val textItem: TextView = view.findViewById(R.id.text_item)

        init {
            view.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(position: Int) {
            textItem.text = dataSet[position]
        }
    }

}
