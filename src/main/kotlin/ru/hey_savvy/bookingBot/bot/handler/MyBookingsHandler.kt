package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.CallbackData
import ru.hey_savvy.bookingBot.bot.MessageSender
import ru.hey_savvy.bookingBot.bot.keyboard.cancelBookingKeyboard
import ru.hey_savvy.bookingBot.bot.router.chatId
import ru.hey_savvy.bookingBot.service.BookingService
import ru.hey_savvy.bookingBot.messages.bookingCancelled
import ru.hey_savvy.bookingBot.messages.bookingInfo
import ru.hey_savvy.bookingBot.messages.noActiveBookings

@Component
class MyBookingsHandler(
    private val messageSender: MessageSender,
    private val bookingService: BookingService
) : Handler {
    override fun handle(update: Update) {
        val chatId = update.chatId()
        val data = update.callbackQuery?.data

        if (data?.startsWith(CallbackData.CANCEL_BOOKING.prefix + ":") == true) cancelBooking(chatId, data)
        else showActiveBookings(chatId)
    }

    private fun showActiveBookings(chatId: Long) {
        val activeBookings = bookingService.getActiveBookingsForClient(chatId)
        if (activeBookings.isNotEmpty()) {
            for (booking in activeBookings) {
                messageSender.sendMessageWithButtons(chatId, bookingInfo(booking), cancelBookingKeyboard(booking))
            }
        }
        else messageSender.sendTextMessage(chatId, noActiveBookings)
    }

    private fun cancelBooking(chatId: Long, data: String) {
        val bookingId = data.substringAfter(":").toLong()

        bookingService.cancelBooking(bookingId)
        messageSender.sendTextMessage(chatId, bookingCancelled)
    }
}