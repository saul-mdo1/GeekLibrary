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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geeklibrary.R
import com.example.geeklibrary.model.Book

@Composable
fun CurrentReadingCard(
    book: Book,
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
                text = book.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = book.author, fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = book.startDate,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = "${book.pageNumber} ${stringResource(R.string.txt_pages)}",
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                IconButton(onClick = { onDetailsClicked(book.id) }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(32.dp)
                    )
                }

                IconButton(onClick = { onUpdateClicked(book.id) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(32.dp)
                    )
                }

                IconButton(onClick = { onDeleteClicked(book.id) }) {
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
fun BookItemCard(
    book: Book,
    cardNumber: Int,
    onUpdateClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit,
    onDetailsClicked: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp),
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
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(4f)
                )

                Text(
                    text = "#$cardNumber",
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(0.5f),
                    color = Color.Gray
                )
            }

            Text(
                text = book.author, fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Text(
                text = "${book.startDate} | ${book.endDate}",
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Text(
                text = "${book.pageNumber} ${stringResource(R.string.txt_pages)}",
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "(${book.rate.toString().replace(".0", "")}/10)",
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 14.sp
                )

                if (book.isFavorite) {
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
                IconButton(onClick = { onDetailsClicked(book.id) }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(32.dp)
                    )
                }

                IconButton(onClick = { onUpdateClicked(book.id) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(32.dp)
                    )
                }

                IconButton(onClick = { onDeleteClicked(book.id) }) {
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