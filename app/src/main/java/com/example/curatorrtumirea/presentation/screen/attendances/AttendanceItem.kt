package com.example.curatorrtumirea.presentation.screen.attendances

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.curatorrtumirea.domain.model.Attendance

@Composable
fun AttendanceItem(
    attendance: Attendance,
    onAttendanceChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = attendance.student.name,
            modifier = Modifier.weight(1f)
        )
        if (attendance.isLoading) {
            Text(
                text = "L"
            )
        }
        Checkbox(checked = attendance.isPresent, onCheckedChange = {
            if (!attendance.isLoading) {
                onAttendanceChanged(it)
            }
        })
    }
}