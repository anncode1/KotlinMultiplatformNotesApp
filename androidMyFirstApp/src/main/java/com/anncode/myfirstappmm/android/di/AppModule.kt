package com.anncode.myfirstappmm.android.di

import android.app.Application
import com.anncode.myfirstappmm.data.local.DatabaseDriverFactory
import com.anncode.myfirstappmm.data.note.SqlDelightNoteDataSource
import com.anncode.myfirstappmm.database.NoteDatabase
import com.anncode.myfirstappmm.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driverFactory: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(NoteDatabase(driverFactory))
    }
}