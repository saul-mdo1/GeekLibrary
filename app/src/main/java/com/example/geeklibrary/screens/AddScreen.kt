@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.geeklibrary.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.geeklibrary.R
import com.example.geeklibrary.composables.DatePickerDocked
import com.example.geeklibrary.composables.FormTopBar
import com.example.geeklibrary.composables.Title
import com.example.geeklibrary.model.Book
import com.example.geeklibrary.model.ElementType
import com.example.geeklibrary.model.Movie
import com.example.geeklibrary.model.Series
import com.example.geeklibrary.utils.StarRatingBar
import com.example.geeklibrary.utils.currentDate
import com.example.geeklibrary.viewModel.MainViewModel

@Composable
fun AddScreen(navController: NavController, type: Int?, vm: MainViewModel) {
    val elementType = ElementType.getTypeById(type)

    val onBackClicked: () -> Unit = {
        val tabIndex = when (elementType) {
            ElementType.BOOK -> 0
            ElementType.MOVIE -> 1
            ElementType.SERIE -> 2
        }
        navController.navigate("home/$tabIndex") {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
    }

    Scaffold(
        topBar = { FormTopBar(onBackClicked) }
    ) { innerPadding ->
        AddForm(
            innerPadding = innerPadding,
            elementType = elementType,
            navController = navController,
            vm = vm
        )
    }
}

@Composable
private fun AddForm(
    innerPadding: PaddingValues,
    elementType: ElementType,
    navController: NavController,
    vm: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .padding(innerPadding)
            .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        //region VALIDATE ELEMENT TYPE
        val isBook by rememberSaveable { mutableStateOf(elementType == ElementType.BOOK) }
        val isMovie by rememberSaveable { mutableStateOf(elementType == ElementType.MOVIE) }
        val isSerie by rememberSaveable { mutableStateOf(elementType == ElementType.SERIE) }
        //endregion

        //region STATES
        var isFinished by rememberSaveable { mutableStateOf(elementType == ElementType.MOVIE) }

        var name by rememberSaveable { mutableStateOf("") }
        var author by rememberSaveable { mutableStateOf("") }
        var pagesNum by rememberSaveable { mutableStateOf("") }
        var seasonNum by rememberSaveable { mutableStateOf("") }
        var startDate by rememberSaveable { mutableStateOf(currentDate()) }
        var endDate by rememberSaveable { mutableStateOf("") }
        var rating by remember { mutableFloatStateOf(1f) }
        var isFavorite by remember { mutableStateOf(false) }
        //endregion

        //region STATES VALIDATIONS
        var isNameValid by remember { mutableStateOf(false) }
        var isAuthorValid by remember { mutableStateOf(false) }
        var isPagesNumValid by remember { mutableStateOf(false) }
        var isSeasonNumValid by remember { mutableStateOf(false) }

        isNameValid = name.isNotBlank()
        isAuthorValid = author.isNotBlank()
        isPagesNumValid = pagesNum.isNotBlank()
        isSeasonNumValid = seasonNum.isNotBlank()

        val isFormValid = when (elementType) {
            ElementType.BOOK -> isNameValid && isAuthorValid && isPagesNumValid
            ElementType.MOVIE -> isNameValid
            ElementType.SERIE -> isNameValid && isSeasonNumValid
        }
        //endregion

        //region TITLE
        Title(elementType = elementType, isUpdate = false)
        //endregion

        //NAME/TITLE
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(text = stringResource(R.string.txt_name))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            maxLines = 1,
            singleLine = true,
            isError = !isNameValid
        )

        if (isBook) {
            //AUTHOR
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = {
                    Text(text = stringResource(R.string.txt_author))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                maxLines = 1,
                singleLine = true,
                isError = !isAuthorValid
            )

            //PAGES NUM
            OutlinedTextField(
                value = pagesNum,
                onValueChange = { pagesNum = it },
                label = {
                    Text(text = stringResource(R.string.txt_pageNum))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = !isPagesNumValid
            )
        }

        if (isSerie) {
            //SEASON NUMBER
            OutlinedTextField(
                value = seasonNum,
                onValueChange = { seasonNum = it },
                label = {
                    Text(stringResource(R.string.txt_seasonNum))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                isError = !isSeasonNumValid
            )
        }

        //START DATE
        DatePickerDocked(
            modifier = Modifier
                .fillMaxWidth(),
            labelText = stringResource(R.string.txt_start_date),
            defaultDate = startDate,
            onDateSelected = { dateSelected ->
                startDate = dateSelected
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isFinished,
                onCheckedChange = { checked ->
                    isFinished = checked
                    endDate = if (checked)
                        currentDate()
                    else
                        ""
                }
            )
            Text(
                text = stringResource(R.string.txt_is_finished),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    isFinished = !isFinished
                    endDate = if (isFinished)
                        currentDate()
                    else
                        ""
                }
            )
        }

        if (isFinished) {
            if(elementType == ElementType.MOVIE)
                endDate = startDate

            //END DATE
            DatePickerDocked(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                labelText = stringResource(R.string.txt_end_date),
                defaultDate = endDate,
                onDateSelected = { dateSelected ->
                    endDate = dateSelected
                }
            )

            //RATE SUBTITLE
            Text(
                text = stringResource(R.string.txt_rate),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            )

            //RATING
            StarRatingBar(
                modifier = Modifier.padding(bottom = 15.dp),
                rating = rating,
                onRatingChanged = { newRating ->
                    rating = newRating
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //IS FAVORITE SUBTITLE
                Text(
                    text = stringResource(R.string.txt_is_favorite),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(end = 5.dp)
                )

                //FAVORITE
                IconButton(
                    modifier = Modifier
                        .size(32.dp),
                    onClick = { isFavorite = !isFavorite })
                {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }

        //SAVE BUTTON
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = Color.Gray
            ),
            onClick = {
                when {
                    isBook -> {
                        val b = Book(
                            title = name,
                            author = author,
                            pageNumber = pagesNum.toInt(),
                            startDate = startDate,
                            endDate = endDate,
                            rate = rating,
                            isFavorite = isFavorite,
                            currentReading = !isFinished && endDate.isBlank()
                        )
                        vm.addBook(b)
                    }

                    isMovie -> {
                        val m = Movie(
                            name = name,
                            startDate = startDate,
                            endDate = endDate,
                            rate = rating,
                            isFavorite = isFavorite
                        )
                        vm.addMovie(m)
                    }

                    isSerie -> {
                        val s = Series(
                            name = name,
                            seasonNum = seasonNum,
                            startDate = startDate,
                            endDate = endDate,
                            rate = rating,
                            isFavorite = isFavorite,
                            currentWatching = !isFinished && endDate.isBlank()
                        )
                        vm.addSerie(s)
                    }
                }
                val tabIndex = when (elementType) {
                    ElementType.BOOK -> 0
                    ElementType.MOVIE -> 1
                    ElementType.SERIE -> 2
                }
                navController.navigate("home/$tabIndex") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            },
            enabled = isFormValid
        ) {
            Text(
                text = stringResource(R.string.txt_save),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}