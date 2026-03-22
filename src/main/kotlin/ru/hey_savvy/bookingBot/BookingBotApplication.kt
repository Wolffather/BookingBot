package ru.hey_savvy.bookingBot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookingBotApplication

fun main(args: Array<String>) {
	runApplication<BookingBotApplication>(*args)
}
