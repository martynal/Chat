package com.chat.di

import android.content.Context
import com.chat.data.db.AppDatabase
import com.chat.domain.FormatDateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.time.ZoneId
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context, CoroutineScope(Dispatchers.Default))

    @Provides
    fun providesFormatDateUseCase(): FormatDateUseCase =
        FormatDateUseCase(ZoneId.systemDefault(), Locale.getDefault())
}
