package com.example.curatorrtumirea.presentation.core.edit_button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.common.conditional

@Composable
fun EditButton(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    emptyPlaceholder: String = "",
    clickable: Boolean = true,
    clearButtonEnabled: Boolean = false,
    onClearClicked: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.conditional(clickable) { this.clickable { onClick() } },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value.ifBlank { emptyPlaceholder },
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    .conditional(value.isBlank()) {
                        this.alpha(0.6f)
                    }
            )
            if (clearButtonEnabled && value.isNotBlank()) {
                IconButton(
                    onClick = { onClearClicked() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "clear")
                }
            }
        }
    }
}