package com.example.geeklibrary.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geeklibrary.R

@Composable
fun SelectItemTypeDialog(
    onDismissRequest: () -> Unit,
    onOptionClicked: (Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text(stringResource(R.string.txt_select_type)) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { onOptionClicked(1) },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.txt_add_book),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Button(
                    onClick = { onOptionClicked(2) },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.txt_add_movie),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Button(
                    onClick = { onOptionClicked(3) }
                ) {
                    Text(
                        text = stringResource(id = R.string.txt_add_serie),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        dismissButton = {},
        confirmButton = {}
    )
}

@Composable
fun DeleteItemDialog(
    onAcceptClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancelClicked() },
        title = { Text(stringResource(R.string.txt_delete_element)) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.txt_delete_element_dialog_text),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 10.dp),
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = { onAcceptClicked() },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.txt_ok),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Button(
                    onClick = { onCancelClicked() },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.txt_cancel)
                    )
                }
            }
        },
        dismissButton = {},
        confirmButton = {}
    )
}