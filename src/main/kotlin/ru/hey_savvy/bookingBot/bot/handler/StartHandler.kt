package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.MessageSender
import ru.hey_savvy.bookingBot.bot.keyboard.mainMenuKeyboard
import ru.hey_savvy.bookingBot.bot.router.chatId
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager

@Component
class StartHandler(
    private val messageSender: MessageSender,
    private val stateManager: ConversationStateManager,
) : Handler {

    override fun handle(update: Update) {
        val keyboard = mainMenuKeyboard()
        val chatId = update.chatId()

        stateManager.resetState(chatId)
        messageSender.sendMessageWithButtons(chatId, "Записаться", keyboard)
    }


}