package ru.hey_savvy.bookingBot.service

import org.springframework.stereotype.Service
import ru.hey_savvy.bookingBot.models.Master
import ru.hey_savvy.bookingBot.repository.MasterRepository
import ru.hey_savvy.bookingBot.repository.TimeSlotRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class TimeSlotService(
    private val timeSlotRepository: TimeSlotRepository,
    private val masterRepository: MasterRepository
) {

    fun getSlotId(timeSlot: LocalDateTime): Long? = timeSlotRepository.findByStartAt(timeSlot)?.id

    fun findAvailableDates(masterId: Long?): List<LocalDate> {
        return withOrWithoutMaster(
            masterId,
            withMaster = { timeSlotRepository.findAvailableDatesByMaster(it) },
            withoutMaster = { timeSlotRepository.findAvailableDates() }

        )
    }

    fun findAvailableTimeSlots(date: LocalDate, masterId: Long?): List<LocalTime> {
        return withOrWithoutMaster(
            masterId,
            withMaster = { timeSlotRepository.findAvailableSlotsByMaster(date, it) },
            withoutMaster = { timeSlotRepository.findAvailableSlots(date) }
        ).map { it.startAt.toLocalTime() }
    }

    private fun <T> withOrWithoutMaster(
        masterId: Long?,
        withMaster: (Master) -> T,
        withoutMaster: () -> T
    ): T
    {
        val master = masterId?.let { masterRepository.findById(masterId).orElse(null) }
        return if (master != null) withMaster(master) else withoutMaster()
    }
}