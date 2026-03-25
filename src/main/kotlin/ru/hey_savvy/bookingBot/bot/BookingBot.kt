package ru.hey_savvy.bookingBot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.handler.BotExceptionHandler
import ru.hey_savvy.bookingBot.bot.router.UpdateRouter
import ru.hey_savvy.bookingBot.bot.router.chatId

@Component
class BookingBot(
    @Value($$"${telegram.bot.token}") private val token: String,
    @Value($$"${telegram.bot.username}") private val username: String,
    private val exceptionHandler: BotExceptionHandler,
) : TelegramLongPollingBot(token) {

    @Autowired
    lateinit var router: UpdateRouter


    override fun getBotUsername() = username

    override fun onUpdateReceived(update: Update) {
        try {
            router.route(update)
        } catch (e: Exception) {
            exceptionHandler.handle(e, update.chatId())
        }
    }
}