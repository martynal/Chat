package com.chat.di

import com.chat.data.db.AppDatabase
import com.chat.data.db.dao.ConversationDao
import com.chat.data.db.dao.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule{

    @Provides
    fun providesMessageDao(
        database: AppDatabase,
    ): MessageDao = database.messageDao()

    @Provides
    fun providesConversationDao(
        database: AppDatabase,
    ): ConversationDao = database.conversationDao()
}