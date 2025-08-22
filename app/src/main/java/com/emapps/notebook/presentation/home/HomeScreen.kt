package com.emapps.notebook.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.emapps.notebook.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("register") }
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_user))
            }
        }
    ) { padding ->
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.padding(padding))
            }

            state.error != null -> {
                Text(
                    stringResource(R.string.error, state.error ?: ""),
                    modifier = Modifier.padding(padding)
                )
            }

            state.users.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.no_users),
                        modifier = Modifier.padding(padding)
                    )
                }
            }

            else -> {
                LazyColumn(contentPadding = padding) {
                    items(state.users) { user ->
                        Text(
                            "Name: ${user.name}, Age: ${user.age}, Job: ${user.jobTitle}, Gender: ${user.gender}"
                        )
                    }
                }
            }
        }
    }
}