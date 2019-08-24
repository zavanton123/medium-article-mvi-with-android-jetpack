package ru.zavanton.unicorn.mvi.fragment

sealed class MviAction {

    data class FindAction(val id: Int) : MviAction()
}

