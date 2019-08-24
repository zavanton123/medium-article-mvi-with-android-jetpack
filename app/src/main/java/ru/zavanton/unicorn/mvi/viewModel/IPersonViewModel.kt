package ru.zavanton.unicorn.mvi.viewModel

import androidx.lifecycle.LiveData
import io.reactivex.subjects.Subject
import ru.zavanton.unicorn.mvi.fragment.MviAction

interface IPersonViewModel {

    fun onViewCreated() // restore the view on orientation change

    fun listenForActions(actions: Subject<MviAction>) // This is input from the view
    fun getViewState(): LiveData<MviViewState> // This is output to the view
}