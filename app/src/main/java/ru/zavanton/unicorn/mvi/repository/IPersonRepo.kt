package ru.zavanton.unicorn.mvi.repository

import io.reactivex.Single

interface IPersonRepo {

    fun getPerson(id: Int): Single<Person>
}
