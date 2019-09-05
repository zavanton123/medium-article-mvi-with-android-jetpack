package ru.zavanton.unicorn.mvi.business.interactor

import io.reactivex.Observable
import ru.zavanton.unicorn.mvi.business.model.MviChange
import ru.zavanton.unicorn.mvi.business.model.MviChange.ErrorChange
import ru.zavanton.unicorn.mvi.business.model.MviChange.NoPersonChange
import ru.zavanton.unicorn.mvi.business.model.MviChange.PersonChange
import ru.zavanton.unicorn.mvi.data.repository.IPersonRepo

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