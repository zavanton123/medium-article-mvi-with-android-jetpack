package ru.zavanton.unicorn.mvi.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.zavanton.unicorn.R
import ru.zavanton.unicorn.mvi.ui.fragment.PersonFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.fmtContainer) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fmtContainer, PersonFragment())
                .commit()
        }
    }
}
