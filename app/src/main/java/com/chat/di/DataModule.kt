package com.chat.di

import com.chat.data.ConversationRepository
import com.chat.data.ConversationRepositoryImpl
import com.chat.data.MessageRepository
import com.chat.data.MessageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindMessageRepositoryImpl(messageRepositoryImpl: MessageRepositoryImpl)
            : MessageRepository

    @Binds
    fun bindConversationRepositoryImpl(conversationRepositoryImpl: ConversationRepositoryImpl)
            : ConversationRepository

}