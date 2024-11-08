package com.example.geeklibrary.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.geeklibrary.R
import com.example.geeklibrary.composables.BookItemCard
import com.example.geeklibrary.composables.CurrentReadingCard
import com.example.geeklibrary.composables.CurrentWatchingCard
import com.example.geeklibrary.composables.DeleteItemDialog
import com.example.geeklibrary.composables.MovieSerieItemCard
import com.example.geeklibrary.model.ElementType
import com.example.geeklibrary.utils.sortByDate
import com.example.geeklibrary.viewModel.MainViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel, initialTabIndex: Int) {
    //region STATES DELETE DIALOG
    var currentElementType by remember { mutableStateOf<ElementType?>(null) }
    var displayDeleteElementDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableIntStateOf(0) }
    //endregion

    Scaffold(
        topBar = { TopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            var tabIndex by remember { mutableIntStateOf(initialTabIndex) }
            val tabs = stringArrayResource(R.array.tabsTitles)

            TabRow(
                selectedTabIndex = 0,
                indicator = { tabPositions ->
                    TabRowDefaults.apply {
                        Divider(
                            modifier = Modifier
                                .height(2.dp)
                                .tabIndicatorOffset(tabPositions[tabIndex]),
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                },
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    ElementTab(
                        title = title,
                        isSelected = tabIndex == index,
                        onTabClicked = { tabIndex = index },
                        icon = painterResource(
                            id = when (index) {
                                0 -> R.drawable.ic_book
                                1 -> R.drawable.ic_movie
                                else -> R.drawable.ic_serie
                            }
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEFEFEF))
                    .padding(8.dp)
            ) {
                when (tabIndex) {
                    0 -> MainScreen(
                        viewModel,
                        navController,
                        ElementType.BOOK,
                        onDeleteClicked = { bookId ->
                            itemToDelete = bookId
                            currentElementType = ElementType.BOOK
                            displayDeleteElementDialog = true
                        }
                    )

                    1 -> MainScreen(
                        viewModel,
                        navController,
                        ElementType.MOVIE,
                        onDeleteClicked = { movieId ->
                            itemToDelete = movieId
                            currentElementType = ElementType.MOVIE
                            displayDeleteElementDialog = true
                        }
                    )

                    2 -> MainScreen(
                        viewModel,
                        navController,
                        ElementType.SERIE,
                        onDeleteClicked = { serieId ->
                            itemToDelete = serieId
                            currentElementType = ElementType.SERIE
                            displayDeleteElementDialog = true
                        }
                    )
                }
            }
        }

        if (displayDeleteElementDialog) {
            DeleteItemDialog(
                onAcceptClicked = {
                    when (currentElementType) {
                        ElementType.BOOK -> viewModel.deleteBook(itemToDelete)
                        ElementType.MOVIE -> viewModel.deleteMovie(itemToDelete)
                        ElementType.SERIE -> viewModel.deleteSerie(itemToDelete)
                        else -> {}
                    }
                    displayDeleteElementDialog = false
                    itemToDelete = 0
                    currentElementType = null
                },
                onCancelClicked = {
                    displayDeleteElementDialog = false
                }
            )
        }

    }
}

@Composable
fun MainScreen(
    vm: MainViewModel,
    navController: NavController,
    type: ElementType,
    onDeleteClicked: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {

            //ADD ELEMENT BUTTON
            AddElementButton(type, navController)

            //TITLE CURRENT READINGS/WATCHING
            if (type != ElementType.MOVIE)
                TitleCurrent(type, vm)

            //CURRENT WATCHING/READING LIST
            when (type) {
                ElementType.BOOK -> CurrentReadingsList(
                    vm,
                    onDetailsClicked = { bookId -> navController.navigate("details/${bookId}/${1}") },
                    onUpdateClicked = { bookId -> navController.navigate("update/${bookId}/${1}") },
                    onDeleteClicked = { bookId -> onDeleteClicked(bookId) }
                )

                ElementType.SERIE -> CurrentSeriesList(
                    vm,
                    onDetailsClicked = { serieId -> navController.navigate("details/${serieId}/${3}") },
                    onUpdateClicked = { serieId -> navController.navigate("update/${serieId}/${3}") },
                    onDeleteClicked = { serieId -> onDeleteClicked(serieId) }
                )

                else -> {}
            }

            //TITLE LAST WATCHED/READ
            TitleAllItems(type, vm)

            //region COMPLETE LIST
            when (type) {
                ElementType.BOOK -> {
                    AllBooksList(
                        vm = vm,
                        onDetailsClicked = { bookId -> navController.navigate("details/${bookId}/${1}") },
                        onUpdateClicked = { bookId -> navController.navigate("update/${bookId}/${1}") },
                        onDeleteClicked = { bookId -> onDeleteClicked(bookId) }
                    )
                }

                ElementType.MOVIE -> {
                    AllMoviesSeriesList(
                        vm = vm,
                        type = ElementType.MOVIE,
                        onDetailsClicked = { movieId -> navController.navigate("details/${movieId}/${2}") },
                        onUpdateClicked = { movieId -> navController.navigate("update/$movieId/${2}") },
                        onDeleteClicked = { movieId -> onDeleteClicked(movieId) }
                    )
                }

                ElementType.SERIE -> {
                    AllMoviesSeriesList(
                        vm = vm,
                        type = ElementType.SERIE,
                        onDetailsClicked = { serieId -> navController.navigate("details/${serieId}/${3}") },
                        onUpdateClicked = { serieId -> navController.navigate("update/$serieId/${3}") },
                        onDeleteClicked = { serieId -> onDeleteClicked(serieId) }
                    )
                }
            }
            //endregion
        }
    }
}

//region SERIES/MOVIES LIST
@Composable
private fun CurrentSeriesList(
    vm: MainViewModel,
    onUpdateClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit,
    onDetailsClicked: (Int) -> Unit
) {
    val allSeries by vm.seriesList.collectAsState()
    val currentWatching = allSeries.filter { it.currentWatching }

    if (currentWatching.isNotEmpty()) {


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            items(currentWatching) { serie ->
                CurrentWatchingCard(
                    serie,
                    onUpdateClicked = {
                        onUpdateClicked(it)
                    },
                    onDeleteClicked = {
                        onDeleteClicked(it)
                    },
                    onDetailsClicked = {
                        onDetailsClicked(it)
                    }
                )
            }
        }
    } else {
        EmptyListsMessage(stringResource(R.string.txt_not_current_watching))
    }
}

@Composable
private fun AllMoviesSeriesList(
    vm: MainViewModel,
    type: ElementType,
    onUpdateClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit,
    onDetailsClicked: (Int) -> Unit
) {
    if (type == ElementType.MOVIE) {
        val movies by vm.moviesList.collectAsState()
        val sortedMovies = movies.sortByDate(ElementType.MOVIE)

        if (sortedMovies.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, max = Dp.Unspecified)
                    .padding(top = 8.dp)
            ) {
                itemsIndexed(sortedMovies) { index, movie ->
                    val reversedIndex = sortedMovies.size - index
                    MovieSerieItemCard(
                        item = movie,
                        elementType = ElementType.MOVIE,
                        cardNumber = reversedIndex,
                        onUpdateClicked = { itemId ->
                            onUpdateClicked(itemId)
                        },
                        onDeleteClicked = { itemId ->
                            onDeleteClicked(itemId)
                        },
                        onDetailsClicked = { itemId ->
                            onDetailsClicked(itemId)
                        }
                    )
                }
            }
        } else {
            EmptyListsMessage(stringResource(R.string.txt_not_watching))
        }
    } else {
        val series by vm.seriesList.collectAsState()
        val sortedSeries = series.sortByDate(ElementType.SERIE).filter { !it.currentWatching }

        if (sortedSeries.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, max = Dp.Unspecified)
                    .padding(top = 8.dp)
            ) {
                itemsIndexed(sortedSeries) { index, serie ->
                    val reversedIndex = sortedSeries.size - index
                    MovieSerieItemCard(
                        item = serie,
                        elementType = ElementType.SERIE,
                        cardNumber = reversedIndex,
                        onUpdateClicked = { itemId ->
                            onUpdateClicked(itemId)
                        },
                        onDeleteClicked = { itemId ->
                            onDeleteClicked(itemId)
                        },
                        onDetailsClicked = { itemId ->
                            onDetailsClicked(itemId)
                        }
                    )
                }
            }
        } else {
            EmptyListsMessage(stringResource(R.string.txt_not_watching))
        }
    }
}
//endregion

//region BOOKS LISTS
@Composable
private fun CurrentReadingsList(
    vm: MainViewModel,
    onUpdateClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit,
    onDetailsClicked: (Int) -> Unit
) {
    val allBooks by vm.booksList.collectAsState()
    val currentReadings = allBooks.filter { it.currentReading }

    if (currentReadings.isNotEmpty()) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            items(currentReadings) { book ->
                CurrentReadingCard(
                    book,
                    onUpdateClicked = { bookId ->
                        onUpdateClicked(bookId)
                    },
                    onDeleteClicked = { bookId ->
                        onDeleteClicked(bookId)
                    },
                    onDetailsClicked = { bookId ->
                        onDetailsClicked(bookId)
                    }
                )
            }
        }
    } else {
        EmptyListsMessage(stringResource(R.string.txt_not_current_readings))
    }
}

@Composable
private fun AllBooksList(
    vm: MainViewModel,
    onUpdateClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit,
    onDetailsClicked: (Int) -> Unit
) {
    val books by vm.booksList.collectAsState()
    val sortedBooks = books.sortByDate(ElementType.BOOK).filter { !it.currentReading }

    if (sortedBooks.isEmpty()) {
        EmptyListsMessage(stringResource(R.string.txt_not_readings))
        return
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(top = 12.dp)
    ) {
        itemsIndexed(sortedBooks) { index, book ->
            val reversedIndex = sortedBooks.size - index
            BookItemCard(
                book,
                reversedIndex,
                onUpdateClicked = { bookId ->
                    onUpdateClicked(bookId)
                },
                onDeleteClicked = { bookId ->
                    onDeleteClicked(bookId)
                },
                onDetailsClicked = { bookId ->
                    onDetailsClicked(bookId)
                }
            )
        }
    }
}
//endregion

//region Home Composables
@Composable
private fun AddElementButton(
    type: ElementType,
    navController: NavController
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Gray
        ),
        onClick = {
            when (type) {
                ElementType.BOOK -> navController.navigate("add-element/${1}")
                ElementType.MOVIE -> navController.navigate("add-element/${2}")
                ElementType.SERIE -> navController.navigate("add-element/${3}")
            }
        }
    ) {
        Text(
            text = when (type) {
                ElementType.BOOK -> stringResource(R.string.txt_add_book)
                ElementType.MOVIE -> stringResource(R.string.txt_add_movie)
                ElementType.SERIE -> stringResource(R.string.txt_add_serie)
            },
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ElementTab(title: String, isSelected: Boolean, onTabClicked: () -> Unit, icon: Painter) {
    Tab(
        text = { Text(title) },
        selected = isSelected,
        onClick = { onTabClicked() },
        icon = {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    )
}

@Composable
private fun TitleAllItems(
    type: ElementType,
    vm: MainViewModel
) {
    Text(
        text = when (type) {
            ElementType.BOOK -> {
                val currentTotal =
                    vm.booksList.collectAsState().value.filter { !it.currentReading }.size
                "${stringResource(R.string.txt_finished_readings)} ($currentTotal)"
            }

            ElementType.MOVIE -> {
                val currentTotal =
                    vm.moviesList.collectAsState().value.size
                "${stringResource(R.string.txt_finished_watching)} ($currentTotal)"
            }

            ElementType.SERIE -> {
                val currentTotal =
                    vm.seriesList.collectAsState().value.filter { !it.currentWatching }.size
                "${stringResource(R.string.txt_finished_watching)} ($currentTotal)"
            }
        },
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
private fun TitleCurrent(
    type: ElementType,
    vm: MainViewModel
) {
    Text(
        text = when (type) {
            ElementType.BOOK -> {
                val currentTotal =
                    vm.booksList.collectAsState().value.filter { it.currentReading }.size
                "${stringResource(R.string.txt_current_readings)} ($currentTotal)"
            }

            else -> {
                val currentTotal =
                    vm.seriesList.collectAsState().value.filter { it.currentWatching }.size
                "${stringResource(R.string.txt_current_watching)} ($currentTotal)"
            }
        },
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar() {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Light
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun EmptyListsMessage(message: String) {
    Text(
        text = message,
        fontStyle = FontStyle.Italic,
        style = MaterialTheme.typography.labelMedium,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}
//endregion