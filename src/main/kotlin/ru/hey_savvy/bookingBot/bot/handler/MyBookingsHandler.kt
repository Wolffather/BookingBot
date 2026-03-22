package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import ru.hey_savvy.bookingBot.bot.MessageSender

@Component
class MyBookingsHandler(
    private val messageSender: MessageSender,
) : Handler {
    override fun handle(chatId: Long) {}
}