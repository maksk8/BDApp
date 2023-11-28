package com.example.mincalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var selectedDate: Calendar
    private lateinit var dateTextView: TextView
    private lateinit var minutesResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentDate: Calendar = Calendar.getInstance()
        selectedDate = Calendar.getInstance()
        dateTextView = findViewById<TextView>(R.id.dateTextView)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)
        dateTextView.text = formattedDate
        minutesResultTextView = findViewById(R.id.tvSeconds)

        val selectDateButton: Button = findViewById<Button>(R.id.selectDateButton)
        selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                updateTextView()
                minutesResultTextView.text = minutesToBirthday().toString()
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun updateTextView() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)
        dateTextView.text = "$formattedDate"
    }

    private fun minutesToBirthday(): Long {
        val currentDate: Calendar = Calendar.getInstance()
        val currentYear = currentDate.get(Calendar.YEAR)
        selectedDate.set(Calendar.YEAR, currentYear)

        if (currentDate.after(selectedDate)) {
            selectedDate.set(Calendar.YEAR, currentYear + 1)
        }
        return minutesBetween(currentDate.time, selectedDate.time)
    }

    //Testing github

    private fun minutesBetween(startDate: Date, endDate: Date): Long {
        val diff = endDate.time - startDate.time
        return diff / (60 * 1000)
    }
}