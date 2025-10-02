package fr.openbonifacio.notes.di

//import fr.openbonifacio.notes.notes.data.database.DatabaseFactory
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import fr.openbonifacio.notes.notes.data.database.DatabaseFactory
import fr.openbonifacio.notes.notes.data.database.NotesDatabase
import fr.openbonifacio.notes.notes.data.repository.DefaultNoteRepository
import fr.openbonifacio.notes.notes.domain.NoteRepository
import fr.openbonifacio.notes.notes.presentation.note_details.NoteDetailsViewModel
import fr.openbonifacio.notes.notes.presentation.notes_list.NotesListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/*koin create instance of Repositories, ViewModels, ...
koin permit to create a separate module for class that need a different implementation depending on the platform
*/

expect val platformModule : Module

val sharedModule = module {
    /*single {
        MyRepositoryImpl(get())
    }.bind<MyRepository>()
    or:
    */
    singleOf(::DefaultNoteRepository).bind<NoteRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<NotesDatabase>().notesDao }

    viewModelOf(::NotesListViewModel)
    viewModel { (noteId: Long) ->
        NoteDetailsViewModel(noteId = noteId, noteRepository = get())
    }
}

//with get(), koin will look all dependencies and here find the DbClient
