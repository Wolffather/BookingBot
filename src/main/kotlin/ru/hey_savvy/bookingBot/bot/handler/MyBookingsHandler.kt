package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.MessageSender

@Component
class MyBookingsHandler(
    private val messageSender: MessageSender,
) : Handler {
    override fun handle(update: Update) {}
}