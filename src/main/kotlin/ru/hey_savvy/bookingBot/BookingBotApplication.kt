package ru.hey_savvy.bookingBot

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookingBotApplication

fun main(args: Array<String>) {
	val flyway = Flyway.configure()
		.dataSource(
			System.getenv("DATASOURCE_URL"),
			System.getenv("DB_USER"),
			System.getenv("DB_PASSWORD")
		)
		.locations("classpath:db/migration")
		.load()
	flyway.migrate()

	runApplication<BookingBotApplication>(*args)
}