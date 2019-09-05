package ru.zavanton.unicorn.mvi.data.repository

import io.reactivex.Single
import ru.zavanton.unicorn.mvi.data.model.Person
import java.util.concurrent.TimeUnit

class PersonRepo : IPersonRepo {

    private val data: MutableMap<Int, Person> = mutableMapOf()

    init {
        data[1] = Person("Mike", "Tyson")
        data[2] = Person("John", "Jackson")
        data[3] = Person("Ann", "Richards")
        data[4] = Person("Nick", "Harrods")
        data[5] = Person("Tom", "Zondson")
    }

    override fun getPerson(id: Int): Single<Person> =
        Single.fromCallable {
            data[id] ?: throw IllegalArgumentException("The user with this ID does not exist!")
        }.delay(1000, TimeUnit.MILLISECONDS)
}
