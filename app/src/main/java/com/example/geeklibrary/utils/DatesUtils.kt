package com.example.geeklibrary.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.geeklibrary.model.Book
import com.example.geeklibrary.model.ElementType
import com.example.geeklibrary.model.Movie
import com.example.geeklibrary.model.Series
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(millis))
}

fun currentDate(): String {
    return try {
        val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
        dateFormat.format(Date())
    } catch (e: Exception) {
        Timber.d("DatesUtils_TAG: currentDate: error ${e.message} ")
        ""
    }
}

fun <T> List<T>.sortByDate(elementType: ElementType): List<T> {
    try {
        val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())

        return when (elementType) {
            ElementType.BOOK -> this.sortedByDescending { item ->
                val date = (item as Book).startDate
                dateFormat.parse(date)
            }

            ElementType.MOVIE -> this.sortedByDescending { item ->
                val date = (item as Movie).startDate
                dateFormat.parse(date)
            }

            ElementType.SERIE -> this.sortedByDescending { item ->
                val date = (item as Series).startDate
                dateFormat.parse(date)
            }
        }
    } catch (e: Exception) {
        Timber.d("DatesUtils_TAG: sortByDate: ERROR: ${e.message} ")
        return this
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDaysDifference(startDate: String?, endDate: String?): Long {
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy")

        val start = LocalDate.parse(startDate, formatter)
        val end = LocalDate.parse(endDate, formatter)

        ChronoUnit.DAYS.between(start, end)
    } catch (e: Exception) {
        Timber.d("_TAG: calculateDaysDifference: ERROR ${e.message} ")
        0
    }
}