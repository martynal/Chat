package com.chat.domain

import com.chat.data.db.model.Message
import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.time.ZoneId
import java.util.*

class FormatDateUseCaseTest {

    val useCase = FormatDateUseCase(
        zoneId = ZoneId.of("Europe/London"),
        locale = Locale.ENGLISH
    )

    @Test
    fun formatDateUseCase() {
        val instant = Instant.parse("2018-10-20T16:55:30.00Z")
        val message = Message("0", "Hi", "0", "0", instant)
        val formatDateUseCase = useCase(message.createdAt)
        Assert.assertEquals(listOf("Saturday","17:55"), formatDateUseCase)
    }
}