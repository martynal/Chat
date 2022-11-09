package com.chat.data.db.model

import androidx.room.*

@Entity(tableName = "conversation")
data class Conversation(
    @PrimaryKey val conversationId: String,
)

@Entity(
    tableName = "conversation_users",
    primaryKeys = ["conversationId", "userId"],
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId")
    ), ForeignKey(
        entity = Conversation::class,
        parentColumns = arrayOf("conversationId"),
        childColumns = arrayOf("conversationId")
    )],
    indices = [
        Index(value = ["conversationId"]),
        Index(value = ["userId"]),
    ],
)
data class ConversationUsersCrossRef(
    val conversationId: String,
    val userId: String
)

data class ConversationWithUsers(
    @Embedded val conversation: Conversation,
    @Relation(
        parentColumn = "conversationId",
        entityColumn = "userId",
        associateBy = Junction(ConversationUsersCrossRef::class, parentColumn = "conversationId", entityColumn = "userId")
    )
    val users: List<User>
)