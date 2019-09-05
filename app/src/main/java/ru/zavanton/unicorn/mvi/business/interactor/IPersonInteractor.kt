package ru.zavanton.unicorn.mvi.business.interactor

import io.reactivex.Observable
import ru.zavanton.unicorn.mvi.business.model.MviChange

interface IPersonInteractor {

    fun fetchPersonById(id: Int): Observable<MviChange>
}