package com.example.mvicomposeproject.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvicomposeproject.R
import com.example.mvicomposeproject.domain.model.GenderUser
import kotlinx.coroutines.flow.Flow

@Composable
fun GenderRoute(
    viewModel: GenderViewModel = hiltViewModel()
) {
    HandleEvents(viewModel.effect)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GenderScreen(
        uiState = uiState,
        onIntent = viewModel::setIntent
    )
}

@Composable
fun GenderScreen(
    modifier: Modifier = Modifier,
    uiState: GenderUiState,
    onIntent: (GenderIntent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Try to find your gender",
            style = MaterialTheme.typography.headlineSmall
        )

        EditTextName(searchGender = { onIntent(GenderIntent.SearchGenderUser(it)) })

        GenderUserInfo(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            isLoading = uiState.isLoading,
            genderUser = uiState.genderUser
        )
    }
}

@Composable
fun EditTextName(searchGender: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = name,
        onValueChange = { name = it },
        label = { Text("Name") },
        maxLines = 1,
        trailingIcon = { 
            IconButton(onClick = { searchGender(name) }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)  
            }
        },
        placeholder = { Text("Enter the name") }
    )
}

@Composable
fun GenderUserInfo(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    genderUser: GenderUser?
) {
    AnimatedContent(
        targetState = isLoading,
        label = "Animated Content"
    ) { targetState ->
        Box(modifier = modifier) {
            if (targetState) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                genderUser?.let { user ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when (user.gender) {
                                "male" -> MaterialTheme.colorScheme.primary
                                "female" -> MaterialTheme.colorScheme.tertiary
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        ),
                        elevation = CardDefaults.cardElevation(5.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(4.dp)
                            ) {
                                Text(
                                    text = user.name,
                                    style = MaterialTheme.typography.displayMedium
                                )

                                Text(
                                    text = stringResource(R.string.gender, user.gender.toString()),
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Text(
                                    text = stringResource(R.string.count, user.count.toString()),
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Text(
                                    text = stringResource(R.string.probability, user.probability.toString()),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HandleEvents(
    events: Flow<GenderEvent>
) {
    val context = LocalContext.current
    events.collectWithLifecycle { event ->
        when (event) {
            is GenderEvent.ShowToast -> {
                Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}




