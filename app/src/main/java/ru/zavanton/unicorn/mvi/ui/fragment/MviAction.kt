package ru.zavanton.unicorn.mvi.ui.fragment

sealed class MviAction {

    data class FindAction(val id: Int) : MviAction()
}

