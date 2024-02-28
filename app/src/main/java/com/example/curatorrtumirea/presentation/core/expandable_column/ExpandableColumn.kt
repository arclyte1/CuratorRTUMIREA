package com.example.curatorrtumirea.presentation.core.expandable_column

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp


@Composable
fun DefaultExpandableColumn(
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    headerTitle: String,
    data: List<String>,
    modifier: Modifier = Modifier,
    itemComposable: @Composable ((String) -> Unit) = { title: String ->
        ExpandableColumnItem(title)
    }
) {
    ExpandableColumn(
        isExpanded = isExpanded,
        onExpandedChanged = onExpandedChanged,
        headerTitle = headerTitle,
        data = data,
        modifier = modifier,
        itemComposable = itemComposable
    )
}

@Composable
fun <T> ExpandableColumn(
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    headerTitle: String,
    data: List<T>,
    modifier: Modifier = Modifier,
    itemComposable: @Composable ((T) -> Unit)
) {
    ExpandableColumn(
        isExpanded = isExpanded,
        data = data,
        header = {
            ExpandableColumnHeader(
                title = headerTitle,
                isExpanded = isExpanded,
                onHeaderClicked = { onExpandedChanged(!isExpanded) }
            )
        },
        modifier = modifier,
        itemComposable = itemComposable
    )
}

@Composable
fun <T> ExpandableColumn(
    isExpanded: Boolean,
    data: List<T>,
    modifier: Modifier = Modifier,
    header: @Composable ColumnScope.() -> Unit,
    itemComposable: @Composable ((T) -> Unit)
) {
    Column(
        modifier = modifier,
    ) {
        header()
        data.forEach {
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                itemComposable(it)
            }
        }
    }
}

@Composable
fun ExpandableColumnHeader(
    title: String,
    isExpanded: Boolean,
    onHeaderClicked: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(modifier = Modifier
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onHeaderClicked() }
        .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1.0f)
        )
        ExpandableColumnHeaderIcon(isExpanded = isExpanded)
    }
}

@Composable
fun ExpandableColumnHeaderIcon(
    isExpanded: Boolean
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 0f else -180f,
        animationSpec = tween(500),
        label = ""
    )

    Icon(
        imageVector = Icons.Default.KeyboardArrowUp,
        modifier = Modifier
            .rotate(rotation),
        contentDescription = "",
    )
}

@Composable
fun ExpandableColumnItem(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}