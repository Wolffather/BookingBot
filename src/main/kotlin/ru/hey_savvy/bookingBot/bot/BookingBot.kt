package ru.hey_savvy.bookingBot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.router.UpdateRouter

@Component
class BookingBot(
    @Value($$"${telegram.bot.token}") private val token: String,
    @Value($$"${telegram.bot.username}") private val username: String
) : TelegramLongPollingBot(token) {

    @Autowired
    lateinit var router: UpdateRouter


    override fun getBotUsername() = username

    override fun onUpdateReceived(update: Update) {
        router.route(update)
    }
}