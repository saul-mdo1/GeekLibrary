package com.example.geeklibrary.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geeklibrary.database.repository.books.BooksRepository
import com.example.geeklibrary.database.repository.movies.MovieRepository
import com.example.geeklibrary.database.repository.series.SeriesRepository
import com.example.geeklibrary.model.Book
import com.example.geeklibrary.model.Movie
import com.example.geeklibrary.model.Series
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val bookRepository: BooksRepository,
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository
) :
    ViewModel() {
    //region LISTS
    private val _booksList = MutableStateFlow(emptyList<Book>())
    val booksList = _booksList.asStateFlow()

    private val _moviesList = MutableStateFlow(emptyList<Movie>())
    val moviesList = _moviesList.asStateFlow()

    private val _seriesList = MutableStateFlow(emptyList<Series>())
    val seriesList = _seriesList.asStateFlow()
    //endregion

    //region MODELS
    private val _book = MutableStateFlow<Book?>(null)
    val book = _book.asStateFlow()

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    private val _serie = MutableStateFlow<Series?>(null)
    val serie = _serie.asStateFlow()
    //endregion

    private var fetchJob: Job? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bookRepository.getAll().collect { booksList ->
                    Timber.d("MainViewModel_TAG: GetAllBooks: ")
                    _booksList.value = booksList
                }
            } catch (e: Exception) {
                Timber.d("MainViewModel_TAG: GetAllBooks: ERROR: ${e.message} ")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                movieRepository.getAll().collect { moviesList ->
                    Timber.d("MainViewModel_TAG: GetAllMovies: ")
                    _moviesList.value = moviesList
                }
            } catch (e: Exception) {
                Timber.d("MainViewModel_TAG: GetAllMovies: ERROR: ${e.message} ")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                seriesRepository.getAll().collect { seriesList ->
                    Timber.d("MainViewModel_TAG: GetAllSeries: ")
                    _seriesList.value = seriesList
                }
            } catch (e: Exception) {
                Timber.d("MainViewModel_TAG: GetAllSeries: ERROR: ${e.message}")
            }
        }
    }

    //region GET MODELS
    fun getBookById(id: Int) {
        Timber.d("MainViewModel_TAG: getMovieById:")
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            bookRepository.getBookById(id).collect {
                _book.value = it
            }
        }
    }

    fun getMovieById(id: Int) {
        Timber.d("MainViewModel_TAG: getMovieById:")
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            movieRepository.getMovieById(id).collect {
                _movie.value = it
            }
        }
    }

    fun getSerieById(id: Int) {
        Timber.d("MainViewModel_TAG: getSerieById:")
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            seriesRepository.getSeriesById(id).collect {
                _serie.value = it
            }
        }
    }

    fun cancelGetByIdFetch() {
        fetchJob?.cancel()
    }
    //endregion

    //region INSERT ELEMENTS
    fun addBook(book: Book) {
        Timber.d("MainViewModel_TAG: addBook:")
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.insertBook(book)
        }
    }

    fun addMovie(movie: Movie) {
        Timber.d("MainViewModel_TAG: addMovie:")
        CoroutineScope(Dispatchers.IO).launch {
            movieRepository.insertMovie(movie)
        }
    }

    fun addSerie(serie: Series) {
        Timber.d("MainViewModel_TAG: addSerie:")
        CoroutineScope(Dispatchers.IO).launch {
            seriesRepository.insertSeries(serie)
        }
    }
    //endregion

    //region UPDATE ELEMENTS
    fun updateBook(book: Book) {
        Timber.d("MainViewModel_TAG: updateBook: ${book.title}")
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.updateBook(book)
        }
    }

    fun updateMovie(movie: Movie) {
        Timber.d("MainViewModel_TAG: updateMovie: ${movie.name}")
        CoroutineScope(Dispatchers.IO).launch {
            movieRepository.updateMovie(movie)
        }
    }

    fun updateSerie(serie: Series) {
        Timber.d("MainViewModel_TAG: updateSerie: ${serie.name}")
        CoroutineScope(Dispatchers.IO).launch {
            seriesRepository.updateSeries(serie)
        }
    }
    //endregion

    //region DELETE ELEMENTS
    fun deleteBook(bookId: Int) {
        Timber.d("MainViewModel_TAG: deleteBook: ")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bookRepository.deleteBook(bookId)
            } catch (e: Exception) {
                Timber.d("MainViewModel_TAG: deleteBook: ERROR: ${e.message}")
            }
        }
    }

    fun deleteMovie(movieId: Int) {
        Timber.d("MainViewModel_TAG: deleteMovie: ")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                movieRepository.deleteMovie(movieId)
            } catch (e: Exception) {
                Timber.d("MainViewModel_TAG: deleteMovie: ERROR: ${e.message}")
            }
        }
    }

    fun deleteSerie(serieId: Int) {
        Timber.d("MainViewModel_TAG: deleteSerie: ")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Timber.d("SeriesRepositoryImpl_TAG: deleteSeries: Deleting series with id: $serieId")
                seriesRepository.deleteSeries(serieId)
            } catch (e: Exception) {
                Timber.d("MainViewModel_TAG: deleteSerie: ERROR ${e.message} ")
            }
        }
    }
    //endregion
}