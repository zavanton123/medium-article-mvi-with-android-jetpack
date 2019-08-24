package ru.zavanton.unicorn.mvi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fmt_person.*
import ru.zavanton.unicorn.R
import ru.zavanton.unicorn.mvi.fragment.MviAction.FindAction
import ru.zavanton.unicorn.mvi.interactor.PersonInteractor
import ru.zavanton.unicorn.mvi.repository.PersonRepo
import ru.zavanton.unicorn.mvi.viewModel.PersonViewModel
import ru.zavanton.unicorn.mvi.viewModel.IPersonViewModel
import ru.zavanton.unicorn.mvi.viewModel.MviViewState

class PersonFragment : Fragment() {

    companion object DemoViewModelFactory : ViewModelProvider.Factory {

        // todo zavanton - replace by di
        private val viewModel: PersonViewModel = PersonViewModel(PersonInteractor(PersonRepo()))

        override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel as T
    }

    private lateinit var viewModel: IPersonViewModel

    private val actions: Subject<MviAction> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModelProviders.of(this, DemoViewModelFactory)
            .get(PersonViewModel::class.java)
            .also {
                viewModel = it
                viewModel.listenForActions(actions)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fmt_person, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onViewCreated()
        setupButton()
        renderViewState()
    }

    private fun setupButton() {
        btnOk.setOnClickListener {
            actions.onNext(FindAction(etInput.text.toString().toInt()))
        }
    }

    private fun renderViewState() {
        viewModel.getViewState().observe(this,
            Observer<MviViewState> {
                renderIsLoading(it.loading)
                renderFirstName(it.firstName)
                renderLastName(it.lastName)
            })
    }

    private fun renderIsLoading(isLoading: Boolean) {
        if (isLoading) {
            tvProgress.visibility = View.VISIBLE
        } else {
            tvProgress.visibility = View.GONE
        }
    }

    private fun renderFirstName(firstName: String) {
        tvFirstName.text = firstName
    }

    private fun renderLastName(lastName: String) {
        tvLastName.text = lastName
    }
}