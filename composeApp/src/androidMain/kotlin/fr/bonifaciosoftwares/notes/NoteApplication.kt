package fr.bonifaciosoftwares.notes

import android.app.Application
import fr.bonifaciosoftwares.notes.di.initKoin
import org.koin.android.ext.koin.androidContext

class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NoteApplication)
        }
    }
}