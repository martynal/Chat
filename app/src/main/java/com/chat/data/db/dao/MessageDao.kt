package com.chat.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chat.data.db.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM message WHERE conversationId=:conversationId ORDER BY createdAt DESC")
    fun getAllByConversation(conversationId: String): Flow<List<Message>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: Message)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(message: List<Message>)
}