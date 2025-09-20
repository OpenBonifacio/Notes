package fr.bonifaciosoftwares.notes.di

import fr.bonifaciosoftwares.notes.notes.data.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseFactory() }
    }