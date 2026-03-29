package ru.hey_savvy.bookingBot.bot

import org.springframework.stereotype.Component
import ru.hey_savvy.bookingBot.messages.masterCancellationNotification
import ru.hey_savvy.bookingBot.messages.masterConfirmationNotification
import ru.hey_savvy.bookingBot.models.Booking

@Component
class NotificationService(
    private val messageSender: MessageSender,
) {

    fun notifyMasterOnConfirm(booking: Booking) {
        messageSender.sendTextMessage(booking.timeSlot.master.telegramChatId, masterConfirmationNotification(booking))
    }

    fun notifyMasterOnCancel(booking: Booking) {
        messageSender.sendTextMessage(booking.timeSlot.master.telegramChatId, masterCancellationNotification(booking))
    }

}