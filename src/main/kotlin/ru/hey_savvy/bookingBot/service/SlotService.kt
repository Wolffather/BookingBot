package ru.hey_savvy.bookingBot.service

import org.springframework.stereotype.Service
import ru.hey_savvy.bookingBot.models.Master
import ru.hey_savvy.bookingBot.models.TimeSlot
import ru.hey_savvy.bookingBot.repository.MasterRepository
import ru.hey_savvy.bookingBot.repository.TimeSlotRepository
import java.time.LocalDateTime

@Service
class SlotService(
    private val masterRepository: MasterRepository,
    private val timeSlotRepository: TimeSlotRepository,
) {

    fun findAvailableSlots(date: LocalDateTime, master: Master?): List<TimeSlot?> {
        return if (master != null) {
            timeSlotRepository.findAvailableSlotsByMaster(date.toLocalDate(), master)

        } else {
            timeSlotRepository.findAvailableSlots(date.toLocalDate())

        }
    }
}