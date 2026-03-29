package ru.hey_savvy.bookingBot.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.state.ConversationState
import ru.hey_savvy.bookingBot.models.Booking
import ru.hey_savvy.bookingBot.models.Status
import ru.hey_savvy.bookingBot.models.TimeSlot
import ru.hey_savvy.bookingBot.repository.BookingRepository
import ru.hey_savvy.bookingBot.repository.MasterRepository
import ru.hey_savvy.bookingBot.repository.ServiceToBookRepository
import ru.hey_savvy.bookingBot.repository.TimeSlotRepository

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val timeSlotRepository: TimeSlotRepository,
    private val serviceToBookRepository: ServiceToBookRepository,
    private val masterRepository: MasterRepository
) {

    @Transactional
    fun createBooking(chatId: Long, update: Update, state: ConversationState): Booking {
        val slot = timeSlotRepository.findById(state.selectedSlotId!!)
            .orElseThrow { NoSuchElementException("Time slot not found") }

        val serviceToBook = serviceToBookRepository.findById(state.selectedServiceId!!)
            .orElseThrow { NoSuchElementException("Service not found") }

        val master = if (state.selectedMasterId != null) masterRepository.findById(state.selectedMasterId).get() else slot.master

        val booking = Booking(
            clientTelegramId = chatId,
            clientName = update.callbackQuery?.from?.firstName ?: "Клиент",
            serviceToBook = serviceToBook,
            timeSlot = slot,
            status = Status.ACTIVE
        )
        val updatedSlot = TimeSlot(
            id = slot.id,
            startAt = slot.startAt,
            isBooked = true,
            master = master
        )

        bookingRepository.save(booking)
        timeSlotRepository.save(updatedSlot)

        return booking
    }

    fun getActiveBookingsForClient(id: Long): List<Booking> = bookingRepository.findAllByClientTelegramIdAndStatus(id, Status.ACTIVE)

    fun cancelBooking(id: Long): Booking {
        val booking = bookingRepository.findById(id).orElseThrow { NoSuchElementException("Booking not found") }
        val timeSlot = booking.timeSlot

        val cancelledBooking = Booking(
            clientTelegramId = booking.clientTelegramId,
            clientName = booking.clientName,
            timeSlot = timeSlot,
            serviceToBook = booking.serviceToBook,
            status = Status.CANCELLED
        )

        val freedUpSlot = TimeSlot(
            id = timeSlot.id,
            startAt = timeSlot.startAt,
            isBooked = false,
            master = timeSlot.master
        )

        bookingRepository.save(cancelledBooking)
        timeSlotRepository.save(freedUpSlot)

        return cancelledBooking
    }


}