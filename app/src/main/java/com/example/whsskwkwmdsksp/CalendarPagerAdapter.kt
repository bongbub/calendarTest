package com.example.whsskwkwmdsksp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whsskwkwmdsksp.databinding.ItemCalendarPageBinding
import java.util.*

class CalendarPagerAdapter(private val onDateClickListener: OnDateClickListener) :
    RecyclerView.Adapter<CalendarPagerAdapter.CalendarPageViewHolder>() {

    private val calendarPages = mutableListOf<Calendar>()

    init {
        val currentCalendar = Calendar.getInstance()
        for (i in -12..12) {
            val calendar = currentCalendar.clone() as Calendar
            calendar.add(Calendar.MONTH, i)
            calendarPages.add(calendar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarPageViewHolder {
        val binding = ItemCalendarPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarPageViewHolder, position: Int) {
        val calendar = calendarPages[position]
        holder.bind(calendar, onDateClickListener)
    }

    override fun getItemCount() = calendarPages.size

    fun getCalendarAtPosition(position: Int): Calendar {
        return calendarPages[position]
    }

    class CalendarPageViewHolder(private val binding: ItemCalendarPageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(calendar: Calendar, clickListener: OnDateClickListener) {
            val adapter = CalendarAdapter(clickListener)
            binding.rvCalendar.apply {
                layoutManager = GridLayoutManager(context, 7)
                this.adapter = adapter
            }

            val calendarItems = generateCalendarItems(calendar)
            adapter.submitList(calendarItems)
        }

        private fun generateCalendarItems(calendar: Calendar): List<CalendarUiState> {
            val calendarItems = mutableListOf<CalendarUiState>()

            val headers = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            for (header in headers) {
                calendarItems.add(CalendarUiState.CalendarHeader(header))
            }
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1 // to start from Sunday
            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            for (i in 0 until firstDayOfWeek) {
                calendarItems.add(CalendarUiState.CalendarDate("", false))
            }

            for (day in 1..daysInMonth) {
                calendarItems.add(CalendarUiState.CalendarDate(day.toString(), true))
            }

            return calendarItems
        }
    }
}