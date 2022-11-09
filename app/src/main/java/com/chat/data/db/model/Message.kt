package com.chat.data.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "message", foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId")
    ), ForeignKey(
        entity = Conversation::class,
        parentColumns = arrayOf("conversationId"),
        childColumns = arrayOf("conversationId")
    )]
)
data class Message(
    @PrimaryKey val messageId: String,
    val text: String,
    val userId: String,
    val conversationId: String,
    val createdAt: Instant,
)
