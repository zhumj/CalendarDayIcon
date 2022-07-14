package com.zhumj.calendardayicon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarDayIcon = findViewById<CalendarDayIcon>(R.id.calendarDayIcon)
        calendarDayIcon.setParam(text = "02")
    }
}