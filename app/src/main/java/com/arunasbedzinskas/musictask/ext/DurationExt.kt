package com.arunasbedzinskas.musictask.ext

import com.arunasbedzinskas.musictask.DURATION_HOURS
import com.arunasbedzinskas.musictask.DURATION_MIN
import com.arunasbedzinskas.musictask.DURATION_SEC
import java.time.Duration

fun Duration.toTrackLength() = buildString {
    val hours = toHours()
    val minutes = minusHours(hours).toMinutes()
    val seconds = minusHours(hours).minusMinutes(minutes).seconds

    if (hours > 0) {
        append(hours)
        append(DURATION_HOURS)
    }
    if (minutes > 0) {
        append(minutes)
        append(DURATION_MIN)
    }
    if (seconds > 0) {
        append(seconds)
        append(DURATION_SEC)
    }

}.trim()