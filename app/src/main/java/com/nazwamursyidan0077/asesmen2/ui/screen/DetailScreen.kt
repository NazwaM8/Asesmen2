package com.nazwamursyidan0077.asesmen2.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nazwamursyidan0077.asesmen2.R
import com.nazwamursyidan0077.asesmen2.ui.theme.Asesmen2Theme
import com.nazwamursyidan0077.asesmen2.util.ViewModelFactory

const val KEY_ID_ANIDRAMA = "idAniDrama"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var titles by remember { mutableStateOf("") }

    var yearRelease by remember { mutableStateOf("") }
    val yearInt = yearRelease.toIntOrNull()
    val yearValid = yearInt in 1800..2025

    var types by remember { mutableStateOf("") }

    var episode by remember { mutableStateOf("") }
    val epsInt = if (types == "Series") episode.toIntOrNull() else 0
    val epsValid = if (types == "Series") epsInt in 1..10000 else true

    var ratings by remember { mutableStateOf("") }
    val ratingInt = ratings.toIntOrNull()
    val ratingValid = ratingInt in 1..10

    val isFormValid = yearValid && epsValid && ratingValid

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getAniDrama(id) ?: return@LaunchedEffect
        titles = data.title
        yearRelease = data.releaseDate.toString()
        types = data.type
        episode = data.episode.toString()
        ratings = data.rating.toString()

    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.add_data))
                    else
                        Text(text = stringResource(id = R.string.edit))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(
                        onClick = {
                            if (titles == "" || yearRelease == "" || types == "" || (types == "Series") && episode == "" || ratings == "") {
                                Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                                return@IconButton
                            }
                            if (id == null) {
                                viewModel.insert(titles, yearInt!!, types, epsInt ?: 0, ratingInt!!)
                            } else (
                                viewModel.update(id, titles, yearInt!!, types, epsInt ?: 0, ratingInt!!)
                            )
                            navController.popBackStack()
                        },
                        enabled = isFormValid
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.save),
                            tint = if (isFormValid) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }

            )
        }
    ) { padding ->
        FormAniDrama(
            title = titles,
            onTitleChange = { titles = it },
            year = yearRelease,
            onYearChange = { yearRelease = it },
            selectedType = types,
            onTypeChange = { types = it },
            eps = episode,
            onEpsChange = { episode = it },
            rating = ratings,
            onRatingChange = { ratings = it },
            yearValid = yearValid,
            epsValid = epsValid,
            ratingValid = ratingValid,
            modifier = Modifier.padding(padding)
        )
        if (id != null && showDialog) {
            DisplayAlertDialogRecycle(
                onDismissRequest = {showDialog = false}) {
                showDialog = false
                viewModel.softDelete(id)
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun FormAniDrama(
    title: String, onTitleChange: (String) -> Unit,
    year: String, onYearChange: (String) -> Unit,
    selectedType: String, onTypeChange: (String) -> Unit,
    eps: String, onEpsChange: (String) -> Unit,
    rating: String, onRatingChange: (String) -> Unit,
    yearValid : Boolean,
    epsValid : Boolean,
    ratingValid : Boolean,
    modifier: Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {onTitleChange(it)},
            label = { Text(text = stringResource(R.string.title)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = year,
            onValueChange = {onYearChange(it)},
            label = { Text(text = stringResource(R.string.year)) },
            singleLine = true,
            isError = !yearValid && year.isNotEmpty(),
            supportingText = {
                if (!yearValid && year.isNotEmpty())
                    Text("Year must be between 1800-2025")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        RadioButtonType(
            selectedType = selectedType,
            onTypeChange = onTypeChange,
            modifier = Modifier.padding(top = 8.dp)
        )
        if (selectedType == "Series") {
            OutlinedTextField(
                value = eps,
                onValueChange = {onEpsChange(it)},
                label = { Text(text = stringResource(R.string.episode)) },
                singleLine = true,
                isError = !epsValid && eps.isNotEmpty(),
                supportingText = {
                    if (!epsValid && eps.isNotEmpty())
                        Text("Episode must be between 1–10000")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        OutlinedTextField(
            value = rating,
            onValueChange = {onRatingChange(it)},
            label = { Text(text = stringResource(R.string.rating)) },
            singleLine = true,
            isError = !ratingValid && rating.isNotEmpty(),
            supportingText = {
                if (!ratingValid && rating.isNotEmpty())
                    Text("Rating must be between 1–10")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RadioButtonType(
    selectedType: String,
    onTypeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val radioOptions = listOf("Series", "Movie")

    Row (
        modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        radioOptions.forEach { text ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (text == selectedType),
                        onClick = { onTypeChange(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedType),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {expanded = true}) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.other),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.recycle_bin))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Asesmen2Theme {
        DetailScreen(rememberNavController())
    }
}