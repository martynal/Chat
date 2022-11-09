package com.chat.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chat.data.db.AppDatabase
import com.chat.data.db.model.Conversation
import com.chat.data.db.model.Message
import com.chat.data.db.model.User
import com.chat.data.db.myUserId
import com.chat.data.db.userSarahId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.Instant
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MessageDaoTest {
    private lateinit var messageDao: MessageDao
    private lateinit var userDao: UserDao
    private lateinit var conversationDao: ConversationDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        messageDao = db.messageDao()
        userDao = db.userDao()
        conversationDao = db.conversationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getAllByConversation() = runTest {
        val instant = Instant.ofEpochMilli(1668009335)
        conversationDao.insert(Conversation("0"))
        userDao.insertAll(listOf(User(myUserId, "Me"), User(userSarahId, "Sarah")))
        messageDao.insertAll(
            listOf(
                Message("2", "Hi 2", myUserId, "0", instant.plusSeconds(6)),
                Message("6", "Hi 6", userSarahId, "0", instant.plus(2, ChronoUnit.HOURS)),
                Message("5", "Hi 5", userSarahId, "0", instant.plusSeconds(29)),
                Message("3", "Hi 3", userSarahId, "0", instant.plusSeconds(7)),
                Message("1", "Hi 1", myUserId, "0", instant.plusSeconds(5)),
                Message("0", "Hi", myUserId, "0", instant),
                Message("4", "Hi 4", userSarahId, "0", instant.plusSeconds(28)),
            )
        )

        val messages = messageDao.getAllByConversation("0")
        assertEquals(
            listOf(
                Message("6", "Hi 6", userSarahId, "0", instant.plus(2, ChronoUnit.HOURS)),
                Message("5", "Hi 5", userSarahId, "0", instant.plusSeconds(29)),
                Message("4", "Hi 4", userSarahId, "0", instant.plusSeconds(28)),
                Message("3", "Hi 3", userSarahId, "0", instant.plusSeconds(7)),
                Message("2", "Hi 2", myUserId, "0", instant.plusSeconds(6)),
                Message("1", "Hi 1", myUserId, "0", instant.plusSeconds(5)),
                Message("0", "Hi", myUserId, "0", instant),
            ), messages.first()
        )
    }
}