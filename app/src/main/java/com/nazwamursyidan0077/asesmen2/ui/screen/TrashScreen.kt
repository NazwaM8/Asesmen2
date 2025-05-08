package com.nazwamursyidan0077.asesmen2.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nazwamursyidan0077.asesmen2.R
import com.nazwamursyidan0077.asesmen2.model.AniDrama
import com.nazwamursyidan0077.asesmen2.ui.theme.Asesmen2Theme
import com.nazwamursyidan0077.asesmen2.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(navController: NavHostController) {
    Scaffold (
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
                    Text(text = stringResource(id = R.string.trash))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        TrashContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun TrashContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: TrashViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Column (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.empty_list),
                fontSize = 30.sp,
                fontFamily = FontFamily.Cursive
            )
        }
    }
    else {
        LazyColumn (
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) {
                ListItemDeleted (
                    aniDrama = it,
                    onDelete = { viewModel.delete(it.id) },
                    onRestore = { viewModel.restore(it.id) })
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ListItemDeleted(aniDrama: AniDrama, onDelete: () -> Unit, onRestore: () -> Unit) {
    Row {
        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = aniDrama.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Black,
                fontSize = 20.sp
            )
            Text(
                text = aniDrama.releaseDate.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(text = aniDrama.type)
            if (aniDrama.type == "Series")
                Text(text = "Eps: " + aniDrama.episode.toString())

            Text(text = "Rating: " + aniDrama.rating.toString())
        }
        DeleteRestore(delete = { onDelete() }, restore = { onRestore() })
    }
}

@Composable
fun DeleteRestore(delete: () -> Unit, restore: () -> Unit) {
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
                    Text(text = stringResource(id = R.string.delete))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.restore))
                },
                onClick = {
                    expanded = false
                    restore()
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TrashScreenPreview() {
    Asesmen2Theme {
        TrashScreen(rememberNavController())
    }
}