package com.chat.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chat.data.db.dao.ConversationDao
import com.chat.data.db.dao.MessageDao
import com.chat.data.db.dao.UserDao
import com.chat.data.db.model.Conversation
import com.chat.data.db.model.ConversationUsersCrossRef
import com.chat.data.db.model.Message
import com.chat.data.db.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val userSarahId = "2eada894-88e5-42c2-9d10-df67fd0f12b8"
const val myUserId = "6a6b4310-5c34-4883-9281-983ead9e1435"
const val conversationId = "af62fc15-ad8b-4614-bdda-6c7649c41720"

@Database(
    entities = [Message::class, User::class, Conversation::class, ConversationUsersCrossRef::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
    abstract fun conversationDao(): ConversationDao

    companion object {
        private const val DATABASE_NAME = "chat-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, coroutineScope: CoroutineScope): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, coroutineScope).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        coroutineScope.launch(Dispatchers.IO) {
                            val adbInstance = getInstance(context, coroutineScope)
                            adbInstance.userDao().insertAll(PREPOPULATE_USER_DATA)
                            adbInstance.conversationDao().insert(PREPOPULATE_CONVERSATION_DATA)
                            adbInstance.conversationDao()
                                .insertAll(PREPOPULATE_CONVERSATION_USERS_DATA)
                        }
                    }
                })
                .build()

        private val PREPOPULATE_USER_DATA = listOf(
            User(
                userSarahId,
                "Sarah",
            ), User(
                myUserId,
                "David",
            )
        )

        private val PREPOPULATE_CONVERSATION_DATA = Conversation(conversationId)
        private val PREPOPULATE_CONVERSATION_USERS_DATA = listOf(
            ConversationUsersCrossRef(
                conversationId,
                userSarahId
            ), ConversationUsersCrossRef(
                conversationId,
                myUserId
            )
        )
    }
}
