package com.example.curatorrtumirea.presentation.shared.event_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme

@Composable
fun EventCard(
    model: EventCardModel,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = model.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            if (model.typeResourceId != null || model.date != null) {
                Row(horizontalArrangement = Arrangement.End) {
                    model.typeResourceId?.let { typeResourceId ->
                        Text(
                            text = stringResource(id = typeResourceId),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    model.date?.let { date ->
                        Text(date)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EventCardPreview() {
    val model = EventCardModel(
        title = "II Международная научно-практическая конференция цифровые международные отношения 2023",
        typeResourceId = R.string.face_to_face_event_type,
        date = "23.10"
    )
    CuratorRTUMIREATheme {
        EventCard(model = model, modifier = Modifier.fillMaxWidth().padding(16.dp))
    }
}