package com.arunasbedzinskas.musictask.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MultiLineListItem(
    firstLineText: String,
    secondLineText: String,
    modifier: Modifier = Modifier,
    endIconButtonPainter: Painter? = null,
    endIconButtonContentDesc: String? = null,
    onIconButtonClick: (() -> Unit)? = null,
    endIconButtonClickEnabled: Boolean = true,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f, fill = false)
            ) {
                Text(
                    text = firstLineText,
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = secondLineText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            endIconButtonPainter?.let {
                IconButton(
                    onClick = { onIconButtonClick?.invoke() },
                    enabled = endIconButtonClickEnabled
                ) {
                    Icon(
                        painter = endIconButtonPainter,
                        contentDescription = endIconButtonContentDesc
                    )
                }
            }
        }
        HorizontalDivider()
    }
}
