package com.example.geeklibrary.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.geeklibrary.R
import com.example.geeklibrary.composables.FormTopBar
import com.example.geeklibrary.model.Book
import com.example.geeklibrary.model.ElementType
import com.example.geeklibrary.model.Movie
import com.example.geeklibrary.model.Series
import com.example.geeklibrary.utils.StarRatingBar
import com.example.geeklibrary.utils.calculateDaysDifference
import com.example.geeklibrary.viewModel.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(navController: NavController, vm: MainViewModel, type: Int?, elementId: Int?) {
    val elementType = ElementType.getTypeById(type)
    //region VALIDATE TYPE
    val isBook by rememberSaveable { mutableStateOf(elementType == ElementType.BOOK) }
    val isSerie by rememberSaveable { mutableStateOf(elementType == ElementType.SERIE) }
    //endregion

    if (elementId != null && elementId != 0) {
        LaunchedEffect(elementId) {
            when (elementType) {
                ElementType.BOOK -> vm.getBookById(elementId)
                ElementType.MOVIE -> vm.getMovieById(elementId)
                ElementType.SERIE -> vm.getSerieById(elementId)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            vm.cancelGetByIdFetch()
        }
    }

    val book by vm.book.collectAsState()
    val movie by vm.movie.collectAsState()
    val serie by vm.serie.collectAsState()

    Scaffold(
        topBar = {
            FormTopBar {
                val tabIndex = when (elementType) {
                    ElementType.BOOK -> 0
                    ElementType.MOVIE -> 1
                    ElementType.SERIE -> 2
                }
                navController.navigate("home/$tabIndex") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFEFEFEF))
                .padding(8.dp)
        ) {
            // ELEMENT NAME
            val name = when (elementType) {
                ElementType.BOOK -> book?.title ?: stringResource(R.string.txt_name)
                ElementType.MOVIE -> movie?.name ?: stringResource(R.string.txt_name)
                ElementType.SERIE -> serie?.name ?: stringResource(R.string.txt_name)
            }
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            )

            if (isBook) {
                //AUTHOR
                val author = book?.author ?: stringResource(R.string.txt_author)
                Text(
                    text = author,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                //PAGES NUMBER
                val pagesNum = stringResource(R.string.txt_pages_placeholder, book?.pageNumber ?: 0)
                Text(
                    text = pagesNum,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            if (isSerie) {
                //SEASON NUMBER
                val seasonNum =
                    stringResource(R.string.txt_season_placeholder, serie?.seasonNum ?: "")
                Text(
                    text = seasonNum,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                )
            }

            // DATES
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DateCard(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    date = when (elementType) {
                        ElementType.BOOK -> book?.startDate?.ifEmpty { stringResource(R.string.txt_empty_value) }
                            ?: stringResource(R.string.txt_empty_value)

                        ElementType.MOVIE -> movie?.startDate?.ifEmpty { stringResource(R.string.txt_empty_value) }
                            ?: stringResource(R.string.txt_empty_value)

                        ElementType.SERIE -> serie?.startDate?.ifEmpty { stringResource(R.string.txt_empty_value) }
                            ?: stringResource(R.string.txt_empty_value)
                    },
                    dateLabel = stringResource(R.string.txt_start_date)
                )

                DateCard(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    date = when (elementType) {
                        ElementType.BOOK -> book?.endDate?.ifEmpty { stringResource(R.string.txt_empty_value) }
                            ?: stringResource(R.string.txt_empty_value)

                        ElementType.MOVIE -> movie?.endDate?.ifEmpty { stringResource(R.string.txt_empty_value) }
                            ?: stringResource(R.string.txt_empty_value)

                        ElementType.SERIE -> serie?.endDate?.ifEmpty { stringResource(R.string.txt_empty_value) }
                            ?: stringResource(R.string.txt_empty_value)
                    },
                    dateLabel = stringResource(R.string.txt_end_date)
                )
            }

            val isFinished by remember(book, movie, serie) {
                when (elementType) {
                    ElementType.BOOK -> mutableStateOf(book?.currentReading == false)
                    ElementType.MOVIE -> mutableStateOf(true)
                    ElementType.SERIE -> mutableStateOf(serie?.currentWatching == false)
                }
            }

            val isFavorite by remember(book, movie, serie) {
                when (elementType) {
                    ElementType.BOOK -> mutableStateOf(book?.isFavorite == true)
                    ElementType.MOVIE -> mutableStateOf(movie?.isFavorite == true)
                    ElementType.SERIE -> mutableStateOf(serie?.isFavorite == true)
                }
            }

            if (isFinished) {
                //DATES DIFFERENCE
                if (elementType != ElementType.MOVIE)
                    ReadingWatchingTimeCard(elementType, book, movie, serie)

                //RATING
                val rating = when (elementType) {
                    ElementType.BOOK -> book?.rate ?: 1f
                    ElementType.MOVIE -> movie?.rate ?: 1f
                    ElementType.SERIE -> serie?.rate ?: 1f
                }

                StarRatingBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    rating = rating,
                    onRatingChanged = {},
                    isRateClickable = false
                )

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "(${rating.toString().replace(".0", "")}/10)",
                        textAlign = TextAlign.Center
                    )

                    if (isFavorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }

                }
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ReadingWatchingTimeCard(
    elementType: ElementType,
    book: Book?,
    movie: Movie?,
    serie: Series?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        val daysDifference = when (elementType) {
            ElementType.BOOK -> calculateDaysDifference(
                book?.startDate,
                book?.endDate
            )

            ElementType.MOVIE -> calculateDaysDifference(
                movie?.startDate,
                movie?.endDate
            )

            ElementType.SERIE -> calculateDaysDifference(
                serie?.startDate,
                serie?.endDate
            )
        }
        Text(
            text = stringResource(
                when (elementType) {
                    ElementType.BOOK -> R.string.txt_reading_time_placeholder
                    else -> R.string.txt_watching_time_placeholder
                },
                daysDifference
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun DateCard(
    modifier: Modifier,
    date: String,
    dateLabel: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = date,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = dateLabel,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}