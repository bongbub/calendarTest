package com.example.whsskwkwmdsksp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.whsskwkwmdsksp.databinding.ItemCalendarDateBinding
import com.example.whsskwkwmdsksp.databinding.ItemCalendarHeaderBinding
import java.util.*


class CalendarAdapter(private val onDateClickListener: OnDateClickListener) :
    ListAdapter<CalendarUiState, RecyclerView.ViewHolder>(calendarUiStateDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CalendarUiState.HEADER_VIEW_TYPE -> CalendarHeaderViewHolder(
                ItemCalendarHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> CalendarDateViewHolder(
                ItemCalendarDateBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CalendarUiState.HEADER_VIEW_TYPE -> (holder as CalendarHeaderViewHolder).bind(getItem(position) as CalendarUiState.CalendarHeader)
            CalendarUiState.DATE_VIEW_TYPE -> (holder as CalendarDateViewHolder).bind(getItem(position) as CalendarUiState.CalendarDate, onDateClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CalendarUiState.CalendarHeader -> CalendarUiState.HEADER_VIEW_TYPE
            is CalendarUiState.CalendarDate -> CalendarUiState.DATE_VIEW_TYPE
        }
    }

    companion object {
        val calendarUiStateDiffUtil = object : DiffUtil.ItemCallback<CalendarUiState>() {
            override fun areItemsTheSame(oldItem: CalendarUiState, newItem: CalendarUiState): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CalendarUiState, newItem: CalendarUiState): Boolean {
                return oldItem == newItem
            }
        }
    }

    class CalendarHeaderViewHolder(private val binding: ItemCalendarHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: CalendarUiState.CalendarHeader) {
            binding.textViewDay.text = header.name
            binding.textViewDay.setTextColor(Color.BLACK)
        }
    }

    class CalendarDateViewHolder(private val binding: ItemCalendarDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: CalendarUiState.CalendarDate, clickListener: OnDateClickListener) {

            val textColor = when (date.dayOfWeek) {
                Calendar.SUNDAY -> ContextCompat.getColor(binding.root.context, R.color.red)
                Calendar.SATURDAY -> ContextCompat.getColor(binding.root.context, R.color.blue)
                else -> ContextCompat.getColor(binding.root.context, R.color.black)
            }

            binding.textViewDay.text = date.date
            binding.textViewDay.setBackgroundResource(date.backgroundResId)
            binding.textViewDay.setTextColor(textColor)
            if (date.isCurrentMonth) {
                binding.textViewDay.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            } else {
                binding.textViewDay.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
            }
            binding.root.setOnClickListener {
                clickListener.onClicked(date)
            }
        }
    }
}
interface OnDateClickListener {
    fun onClicked(calendarDate: CalendarUiState.CalendarDate)
}
