package com.example.geeklibrary.utils

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.geeklibrary.R

@Composable
fun StarRatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    isRateClickable: Boolean = true
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val maxRating = 10
        for (i in 1..maxRating) {
            val icon = when {
                i <= rating -> painterResource(R.drawable.star_foreground)
                i - 0.5f == rating -> painterResource(R.drawable.half_star)
                else -> painterResource(R.drawable.star_background)
            }

            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                if (isRateClickable) {
                                    val halfClicked = offset.x < size.width / 2
                                    val newRating = if (halfClicked) i - 0.5f else i.toFloat()
                                    onRatingChanged(newRating)
                                }
                            }
                        )
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}