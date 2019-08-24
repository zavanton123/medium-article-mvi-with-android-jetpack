package ru.zavanton.unicorn.mvi.interactor

import ru.zavanton.unicorn.mvi.repository.Person

sealed class MviChange {

    object LoadingChange : MviChange()

    data class PersonChange(val person: Person) : MviChange()

    object NoPersonChange : MviChange()

    data class ErrorChange(val throwable: Throwable) : MviChange()
}

