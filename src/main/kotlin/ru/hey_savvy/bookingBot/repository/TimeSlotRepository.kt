package ru.hey_savvy.bookingBot.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.hey_savvy.bookingBot.models.Master
import ru.hey_savvy.bookingBot.models.TimeSlot
import java.time.LocalDate

@Repository
interface TimeSlotRepository: JpaRepository<TimeSlot, Long> {

    //fun findAvailableTimeSlots(): List<TimeSlot>

    @Query("SELECT t FROM TimeSlot t WHERE CAST(t.startAt AS LocalDate) = :date AND t.isBooked = false AND t.master = :master")
    fun findAvailableSlotsByMaster(
        @Param("date") date: LocalDate,
        @Param("master") master: Master
    ): List<TimeSlot>

    @Query("SELECT t FROM TimeSlot t WHERE CAST(t.startAt AS LocalDate) = :date AND t.isBooked = false")
    fun findAvailableSlots(
        @Param("date") date: LocalDate
    ): List<TimeSlot>
}