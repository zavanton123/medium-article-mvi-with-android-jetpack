package ru.zavanton.unicorn.mvi.ui.viewModel

data class MviViewState(
    val firstName: String = "",
    val lastName: String = "",
    val loading: Boolean = false
)