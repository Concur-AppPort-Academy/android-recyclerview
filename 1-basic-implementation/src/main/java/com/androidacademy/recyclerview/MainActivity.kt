package com.androidacademy.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
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

    private fun setRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ItemAdapter(mutableListOf(Recipe("Cake","This is a cake recipe",
                listOf(),true,"Im the owner"))){ position->
                onItemClicked(position)
            }
        }

        val button = findViewById<Button>(R.id.addButton)
        button.setOnClickListener{
            val adapter = recyclerView.adapter as ItemAdapter
            adapter.addItem(Recipe("Cake","This is a cake recipe",
                listOf(),true,"Im the owner"))
            adapter.notifyDataSetChanged()
        }
    }

    private fun onItemClicked(item: Int){
        Toast.makeText(this, "Item Clicked $item", Toast.LENGTH_SHORT).show()
    }
}

class ItemAdapter(private val dataSet: MutableList<Recipe>, private val onItemClicked: (Int) -> Unit): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.line_item, parent, false)
        return ItemViewHolder(view, onItemClicked = onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(position)
    override fun getItemCount(): Int = dataSet.size

    fun addItem(value: Recipe, position: Int = 0){
        dataSet.add(position, value)
    }

    inner class ItemViewHolder(view: View, val onItemClicked: (Int) -> Unit): RecyclerView.ViewHolder(view) {
        private val recipeName: TextView = view.findViewById(R.id.recipe_name)
        private val recipeDescription: TextView = view.findViewById(R.id.recipe_description)
        private val recipeOwner: TextView = view.findViewById(R.id.recipe_owner)
        private val recipeIsFavorite: ImageButton = view.findViewById(R.id.recipe_is_favorite)
        init {
            view.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(position: Int) {
            val recipe = dataSet[position]
            recipeName.text = recipe.name
            recipeDescription.text = recipe.description
            recipeOwner.text = recipe.owner
            if(recipe.isFavorite) {
                recipeIsFavorite.visibility= View.VISIBLE
            } else {
                recipeIsFavorite.visibility= View.GONE
            }
        }
    }
}

class Recipe(
    val name:String,
    val description:String,
    val steps:List<String>,
    var isFavorite:Boolean,
    val owner:String
)