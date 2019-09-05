package ru.zavanton.unicorn.mvi.business.model

import ru.zavanton.unicorn.mvi.data.model.Person

sealed class MviChange {

    object LoadingChange : MviChange()

    data class PersonChange(val person: Person) : MviChange()

    object NoPersonChange : MviChange()

    data class ErrorChange(val throwable: Throwable) : MviChange()
}

