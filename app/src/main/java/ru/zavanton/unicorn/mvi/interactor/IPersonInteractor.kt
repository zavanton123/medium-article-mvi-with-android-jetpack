package ru.zavanton.unicorn.mvi.interactor

import io.reactivex.Observable

interface IPersonInteractor {

    fun fetchPersonById(id: Int): Observable<MviChange>
}