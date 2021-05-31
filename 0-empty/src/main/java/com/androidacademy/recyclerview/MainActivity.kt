package com.androidacademy.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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

    }
}
