package ru.hey_savvy.bookingBot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import ru.hey_savvy.bookingBot.bot.BookingBot
import ru.hey_savvy.bookingBot.bot.MessageSender

@Configuration
class BotConfig (
    private val bot : BookingBot,
    private val messageSender: MessageSender
){

    @Bean
    fun getBotApi() : TelegramBotsApi {
        messageSender.sender = bot
        val api = TelegramBotsApi(DefaultBotSession::class.java)
        try {
            api.registerBot(bot)

        } catch (e: TelegramApiRequestException) {
            e.printStackTrace() //TODO: add proper exception handling
        }
        return api
    }
}