package com.example.curatorrtumirea.presentation.screen.email_confirmation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme

@Composable
fun ConfirmationCodeTextField(
    value: String,
    onValueChange: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    charCount: Int = 4,
    fontSize: TextUnit = 32.sp
) {
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(value, selection = TextRange(value.length)),
        onValueChange = {
            if (it.text.length <= charCount) {
                onValueChange(it.text, it.text.length == charCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(charCount) { index ->
                    CharView(
                        index = index,
                        text = value,
                        fontSize = fontSize
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        },
        readOnly = readOnly
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    fontSize: TextUnit,
) {
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width((fontSize.value * 1.25).dp)
            .height((fontSize.value * 1.5).dp)
            .padding(2.dp)
            .clip(RoundedCornerShape((fontSize.value / 4).dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        text = char,
        textAlign = TextAlign.Center,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
@Preview
fun ConfirmationCodeTextFieldPreview() {
    var code by remember { mutableStateOf("123") }
    CuratorRTUMIREATheme {
        ConfirmationCodeTextField(
            value = code,
            onValueChange = { it, _ ->  code = it },
            modifier = Modifier.fillMaxWidth(),
            charCount = 4,
            fontSize = 40.sp
        )
    }
}