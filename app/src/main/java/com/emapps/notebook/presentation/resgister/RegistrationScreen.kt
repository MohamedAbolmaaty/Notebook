package com.emapps.notebook.presentation.resgister

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.emapps.notebook.R

@Composable
fun RegistrationScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavController
) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.name,
            onValueChange = { viewModel.onIntent(RegisterIntent.UpdateName(it)) },
            label = { Text(stringResource(R.string.name)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state.age.toString(),
            onValueChange = { viewModel.onIntent(RegisterIntent.UpdateAge(it.toInt())) },
            label = { Text(stringResource(R.string.age)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state.jobTitle,
            onValueChange = { viewModel.onIntent(RegisterIntent.UpdateJobTitle(it)) },
            label = { Text(stringResource(R.string.job_title)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state.gender,
            onValueChange = { viewModel.onIntent(RegisterIntent.UpdateGender(it)) },
            label = { Text(stringResource(R.string.gender)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.onIntent(RegisterIntent.Register) },
            enabled = !state.isSaving,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.register))
        }
        if (state.isSaving) {
            CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally))
        }
        if (state.error != null) {
            Text(text = state.error ?: "", color = MaterialTheme.colorScheme.error)
        }
        if (state.saveSuccess) {
            Text(stringResource(R.string.saved_successfully))
            navController.navigate("home")
        }
    }
}