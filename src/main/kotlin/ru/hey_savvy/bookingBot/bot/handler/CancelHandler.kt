package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.router.chatId
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager

@Component
class CancelHandler(
    private val startHandler: StartHandler,
    private val stateManager: ConversationStateManager
) : Handler {
    override fun handle(update: Update) {
        val chatId = update.chatId()
        stateManager.resetState(chatId)

        startHandler.handle(update)
    }
}