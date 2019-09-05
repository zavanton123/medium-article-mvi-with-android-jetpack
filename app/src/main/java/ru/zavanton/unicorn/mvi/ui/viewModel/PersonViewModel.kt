package ru.zavanton.unicorn.mvi.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.Subject
import ru.zavanton.unicorn.mvi.ui.fragment.MviAction
import ru.zavanton.unicorn.mvi.ui.fragment.MviAction.FindAction
import ru.zavanton.unicorn.mvi.business.interactor.IPersonInteractor
import ru.zavanton.unicorn.mvi.business.model.MviChange
import ru.zavanton.unicorn.mvi.business.model.MviChange.*
import ru.zavanton.unicorn.mvi.utils.Log

class PersonViewModel(
    private val interactor: IPersonInteractor
) : ViewModel(), IPersonViewModel {

    private val viewState: MutableLiveData<MviViewState> = MutableLiveData()

    private val changesDisposable = CompositeDisposable()
    private val actionsDisposable = CompositeDisposable()

    private val reducer: (MviViewState, MviChange) -> MviViewState =
        { initialViewState, change ->
            when (change) {
                is LoadingChange -> processLoadingChange(initialViewState)
                is PersonChange -> processPersonChange(initialViewState, change)
                is NoPersonChange -> processNoPersonChange(initialViewState)
                is ErrorChange -> processErrorChange(initialViewState)
            }.also { newViewState ->
                viewState.value = newViewState
            }
        }

    init {
        Log.d("zavanton - PersonViewModel is init")
        viewState.value = MviViewState(firstName = "Anton", lastName = "Zavyalov", loading = false)
    }

    override fun onViewCreated() {
        // to restore the view - pass a copy of the existing value to viewState
        viewState.value = viewState.value?.copy()
    }

    override fun getViewState(): LiveData<MviViewState> = viewState

    override fun listenForActions(actions: Subject<MviAction>) {
        actionsDisposable.add(
            actions.subscribe { action ->
                when (action) {
                    is FindAction -> processFindById(action)
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("zavanton - onCleared")

        if (!changesDisposable.isDisposed) {
            changesDisposable.dispose()
        }
        if (!actionsDisposable.isDisposed) {
            actionsDisposable.dispose()
        }
    }

    private fun processFindById(findAction: FindAction) {
        val loadPersonObservable: Observable<MviChange> =
            interactor.fetchPersonById(findAction.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(LoadingChange)

        viewState.value?.apply {
            changesDisposable.add(loadPersonObservable
                .scan(this, reducer) // reduce
                .distinctUntilChanged()
                .doOnNext { Log.d("zavanton - received state: $it") }
                .subscribe(
                    { state -> viewState.value = state }, // render state
                    { Log.e(it, "zavanton - error while loading person: $it") }
                )
            )
        }
    }

    private fun processLoadingChange(initialViewState: MviViewState): MviViewState =
        initialViewState.copy(loading = true)

    private fun processPersonChange(initialViewState: MviViewState, demoChange: PersonChange): MviViewState =
        initialViewState.copy(
            firstName = demoChange.person.firstName,
            lastName = demoChange.person.lastName,
            loading = false
        )

    private fun processNoPersonChange(initialViewState: MviViewState): MviViewState =
        initialViewState.copy(
            firstName = "Not Available",
            lastName = "Not Available",
            loading = false
        )

    private fun processErrorChange(initialViewState: MviViewState): MviViewState =
        initialViewState.copy(
            firstName = "Error",
            lastName = "Error",
            loading = false
        )
}