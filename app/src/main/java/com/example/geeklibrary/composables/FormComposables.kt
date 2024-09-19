package com.example.geeklibrary.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geeklibrary.R
import com.example.geeklibrary.model.ElementType
import com.example.geeklibrary.utils.convertMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTopBar(onBackClicked: () -> Unit) {
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
        ),
        navigationIcon = {
            IconButton(
                onClick = { onBackClicked() }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Back"
                )
            }
        }
    )
}


@Composable
fun Title(elementType: ElementType, isUpdate: Boolean) {
    Text(
        text = when (elementType) {
            ElementType.BOOK -> stringResource(if (isUpdate) R.string.txt_update_book else R.string.txt_add_book)
            ElementType.MOVIE -> stringResource(if (isUpdate) R.string.txt_update_movie else R.string.txt_add_movie)
            ElementType.SERIE -> stringResource(if (isUpdate) R.string.txt_update_serie else R.string.txt_add_serie)
        },
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    modifier: Modifier,
    labelText: String,
    defaultDate: String? = null,
    onDateSelected: (String) -> Unit
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    var selectedDate by rememberSaveable { mutableStateOf(defaultDate ?: "") }

    LaunchedEffect(defaultDate){
        selectedDate = defaultDate ?: ""
    }

    //region INPUT DISPLAYING DATE
    Box(
        modifier = modifier
            .height(74.dp)
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = {
                Text(labelText)
            },
            readOnly = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = LocalContentColor.current.copy(1f),
                disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(1f),
                disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
            ),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date",
                        tint = MaterialTheme.colorScheme.onSurface.copy(1f)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePicker = !showDatePicker
                }
        )
    }
    //endregion

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedDate =
                            convertMillisToDate(datePickerState.selectedDateMillis ?: 0)
                        showDatePicker = false
                        onDateSelected(selectedDate)
                    }
                ) {
                    Text(stringResource(R.string.txt_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.txt_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

