package ru.hey_savvy.bookingBot.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.hey_savvy.bookingBot.models.Booking
import ru.hey_savvy.bookingBot.models.Status

@Repository
interface BookingRepository : JpaRepository<Booking, Long> {
    fun findAllByClientTelegramIdAndStatus(clientTelegramId: Long, status: Status): List<Booking>
}