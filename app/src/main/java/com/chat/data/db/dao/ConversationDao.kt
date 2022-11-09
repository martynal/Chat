package com.chat.data.db.dao

import androidx.room.*
import com.chat.data.db.model.Conversation
import com.chat.data.db.model.ConversationUsersCrossRef
import com.chat.data.db.model.ConversationWithUsers
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {

    @Transaction
    @Query("SELECT * FROM conversation WHERE conversationId=:conversationId")
    fun getConversationWithUsers(conversationId: String): Flow<ConversationWithUsers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(conversation: Conversation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(conversationUsersCrossRefs: List<ConversationUsersCrossRef>)

}