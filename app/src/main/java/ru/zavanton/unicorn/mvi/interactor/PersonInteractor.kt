package ru.zavanton.unicorn.mvi.interactor

import io.reactivex.Observable
import ru.zavanton.unicorn.mvi.interactor.MviChange.ErrorChange
import ru.zavanton.unicorn.mvi.interactor.MviChange.NoPersonChange
import ru.zavanton.unicorn.mvi.interactor.MviChange.PersonChange
import ru.zavanton.unicorn.mvi.repository.IPersonRepo

class PersonInteractor(
    private val repo: IPersonRepo
) : IPersonInteractor {

    override fun fetchPersonById(id: Int): Observable<MviChange> =
        repo.getPerson(id)
            .toObservable()
            .map<MviChange> { PersonChange(it) }
            .defaultIfEmpty(NoPersonChange)
            .onErrorReturn { ErrorChange(it) }
}