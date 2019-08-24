package ru.zavanton.unicorn.mvi.viewModel

data class MviViewState(
    val firstName: String = "",
    val lastName: String = "",
    val loading: Boolean = false
)