package com.chat.domain

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class FormatDateUseCase @Inject constructor(
    private val zoneId: ZoneId,
    private val locale: Locale,
) {
    operator fun invoke(
        instant: Instant,
    ): List<String> {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(zoneId).withLocale(locale)
        return formatter.format(instant).split(" ")
    }
}

private const val PATTERN_FORMAT = "EEEE HH:mm"