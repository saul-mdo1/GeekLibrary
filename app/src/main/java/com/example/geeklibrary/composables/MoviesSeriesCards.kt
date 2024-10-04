package com.example.geeklibrary.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geeklibrary.R
import com.example.geeklibrary.model.ElementType
import com.example.geeklibrary.model.Movie
import com.example.geeklibrary.model.Series

@Composable
fun CurrentWatchingCard(
    serie: Series,
    onUpdateClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit,
    onDetailsClicked: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = serie.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 19.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            ) {
                Text(
                    text = stringResource(R.string.txt_season),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 6.dp, end = 6.dp)
                )
                Text(
                    text = serie.seasonNum,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp
                )
            }

            Text(
                text = serie.startDate,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                IconButton(onClick = { onDetailsClicked(serie.id) }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(32.dp)
                    )
                }

                IconButton(onClick = { onUpdateClicked(serie.id) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(32.dp)
                    )
                }

                IconButton(onClick = { onDeleteClicked(serie.id) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun <T> MovieSerieItemCard(
    item: T,
    elementType: ElementType,
    cardNumber: Int,
    onUpdateClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit,
    onDetailsClicked: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val name =
                    if (elementType == ElementType.MOVIE) (item as Movie).name else (item as Series).name
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 19.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(3f)

                )
                Text(
                    text = "#$cardNumber",
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Top),
                    color = Color.Gray
                )
            }

            val seasonNumVisible = item is Series

            if (seasonNumVisible) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                ) {
                    Text(
                        text = stringResource(R.string.txt_season),
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 6.dp, end = 6.dp)
                    )
                    Text(
                        text = (item as Series).seasonNum,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 16.sp
                    )
                }
            }

            val startDate =
                if (elementType == ElementType.MOVIE) (item as Movie).startDate else (item as Series).startDate
            val endDate =
                if (elementType == ElementType.MOVIE) (item as Movie).endDate else (item as Series).endDate
            val dateRange =
                if (elementType == ElementType.MOVIE) startDate else "$startDate | $endDate"
            Text(
                text = dateRange,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val rate =
                    if (elementType == ElementType.MOVIE) (item as Movie).rate else (item as Series).rate
                Text(
                    text = "(${rate.toString().replace(".0", "")}/10)",
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 14.sp
                )

                val isFavorite =
                    if (elementType == ElementType.MOVIE) (item as Movie).isFavorite else (item as Series).isFavorite
                if (isFavorite) {
                    Icon(
                        painter = painterResource(R.drawable.star_foreground),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(start = 6.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                IconButton(
                    onClick = {
                        val id =
                            if (elementType == ElementType.MOVIE) (item as Movie).id else (item as Series).id
                        onDetailsClicked(id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(32.dp)
                    )
                }

                IconButton(
                    onClick = {
                        val id =
                            if (elementType == ElementType.MOVIE) (item as Movie).id else (item as Series).id
                        onUpdateClicked(id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(32.dp)
                    )
                }

                IconButton(
                    onClick = {
                        val id =
                            if (elementType == ElementType.MOVIE) (item as Movie).id else (item as Series).id
                        onDeleteClicked(id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            }
        }
    }
}