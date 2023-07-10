package com.nwcode.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CommonComposable {
    @Composable
    fun PropertyRow(propertyName: String, value: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = propertyName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    fun formatDate(timestamp: Long, showDay:Boolean = true): String {
        val format: SimpleDateFormat;
        if(showDay) {
            format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        }
        else {
            format = SimpleDateFormat("HH:mm", Locale.getDefault())
        }

        val date = Date(timestamp * 1000) // Multiply by 1000 if the timestamp is in seconds
        format.timeZone = TimeZone.getTimeZone("GMT+03:00")
        return format.format(date)
    }
}