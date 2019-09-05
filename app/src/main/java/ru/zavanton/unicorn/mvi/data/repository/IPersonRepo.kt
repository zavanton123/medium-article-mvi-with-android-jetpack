package ru.zavanton.unicorn.mvi.data.repository

import io.reactivex.Single
import ru.zavanton.unicorn.mvi.data.model.Person

interface IPersonRepo {

    fun getPerson(id: Int): Single<Person>
}
