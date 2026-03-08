package com.codingchallenge.postcommentapp.presenter.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.conditional(
    condition: Boolean,
    modifier: @Composable Modifier.() -> Modifier,
) = if (condition) this then Modifier.modifier() else this