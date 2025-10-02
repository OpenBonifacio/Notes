package fr.openbonifacio.notes.di

import fr.openbonifacio.notes.notes.data.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseFactory() }
    }