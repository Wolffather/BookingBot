package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import ru.hey_savvy.bookingBot.bot.MessageSender
import ru.hey_savvy.bookingBot.bot.keyboard.mainMenuKeyboard

@Component
class StartHandler(
    private val messageSender: MessageSender,
) : Handler {

    override fun handle(chatId: Long, ) {
        val keyboard = mainMenuKeyboard()
        messageSender.sendMessageWithButtons(chatId, "Hello", keyboard)
    }
}