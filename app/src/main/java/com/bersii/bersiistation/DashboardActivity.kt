package com.bersii.bersiistation

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import org.w3c.dom.Text

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val sharedPreference =  getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val token = sharedPreference.getString("token","defaultToken").toString()
        val view = findViewById<TextView>(R.id.test)
        view.text = token
    }
}