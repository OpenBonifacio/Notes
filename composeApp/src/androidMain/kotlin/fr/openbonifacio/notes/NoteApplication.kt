package fr.openbonifacio.notes

import android.app.Application
import fr.openbonifacio.notes.di.initKoin
import org.koin.android.ext.koin.androidContext

class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NoteApplication)
        }
    }
}