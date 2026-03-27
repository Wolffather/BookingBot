package ru.hey_savvy.bookingBot.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalDate.toDisplayString(): String =
    this.format(DateTimeFormatter.ofPattern("dd.MM"))

fun LocalTime.toDisplayString(): String =
    this.format(DateTimeFormatter.ofPattern("hh:mm"))

fun LocalDateTime.toDisplayString(): String =
    this.format(DateTimeFormatter.ofPattern("dd.MM HH:mm"))