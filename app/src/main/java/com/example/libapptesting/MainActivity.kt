package com.example.libapptesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonA = findViewById<Button>(R.id.buttaaaaaa)
        buttonA.setOnClickListener{
//            val logger =
        }
    }
}