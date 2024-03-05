package com.example.mvicomposeproject.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

// Based on https://stackoverflow.com/a/70185157/4385799

@Composable
inline fun <reified T> Flow<T>.collectWithLifecycle(
    key: Any = Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    val lifecycleAwareFlow = remember(this, lifecycleOwner) {
        flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
            minActiveState = minActiveState
        )
    }

    LaunchedEffect(key) {
        lifecycleAwareFlow.collect { action(it) }
    }
}