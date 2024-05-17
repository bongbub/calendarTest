package com.example.whsskwkwmdsksp

import java.util.UUID

sealed class CalendarUiState(val id: String = UUID.randomUUID().toString()) {

    // 헤더 데이터 클래스로 양식 설정
    data class CalendarHeader(
        val name: String,
        val backgroundResId:Int = R.drawable.shape_calendar_header
    ) : CalendarUiState()

    // 날짜(일) 데이터 클래스로 양식 설정
    data class CalendarDate(
        val date: String,
        val isCurrentMonth: Boolean,
        val dayOfWeek: Int,
        val backgroundResId: Int = R.drawable.shape_calendar_date
    ) : CalendarUiState()

    companion object {
        const val HEADER_VIEW_TYPE = 1
        const val DATE_VIEW_TYPE = 2
    }
}
