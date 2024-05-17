package com.example.whsskwkwmdsksp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.whsskwkwmdsksp.databinding.ActivityMainBinding
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity(), OnDateClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var calendarAdapter: CalendarPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calendarAdapter = CalendarPagerAdapter(this)
        binding.viewPager.adapter = calendarAdapter

        binding.viewPager.setCurrentItem(12, false)

        updateYearMonth(12)
        
        
        // 뷰페이저의 페이지 변경 이벤트 감지해서 연도/월 업뎃
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateYearMonth(position)
            }
        })


    }private fun updateYearMonth(position: Int) {
        val calendar = calendarAdapter.getCalendarAtPosition(position)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        binding.textViewYearMonth.text = "$year 년 $month"
    }

    override fun onClicked(calendarDate: CalendarUiState.CalendarDate) {
        // 날짜 클릭처리
    }
}

        /*calendarAdapter = CalendarAdapter(this)
        binding.rvCalendar.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 7)
            adapter = calendarAdapter
        }

        val calendarItems = generateCalendarItems()
        calendarAdapter.submitList(calendarItems)*/

/*
    private fun generateCalendarItems(): List<CalendarUiState> {
        val calendarItems = mutableListOf<CalendarUiState>()

        // Add headers (Sun, Mon, Tue, etc.)
        val headers = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        for (header in headers) {
            calendarItems.add(CalendarUiState.CalendarHeader(header))
        }

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1 // to start from Sunday
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Add empty days for the first week
        for (i in 0 until firstDayOfWeek) {
            calendarItems.add(CalendarUiState.CalendarDate("", false))
        }

        // Add days of the month
        for (day in 1..daysInMonth) {
            calendarItems.add(CalendarUiState.CalendarDate(day.toString(), true))
        }

        return calendarItems
    }*/


