package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import ru.hey_savvy.bookingBot.bot.MessageSender
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager

@Component
class BotExceptionHandler(
    private val messageSender: MessageSender,
    private val stateManager: ConversationStateManager
) {
    fun handle(e: Exception, chatId: Long) {
        when (e) {
            is NoSuchElementException ->
                messageSender.sendTextMessage(chatId, "Запись не найдена. Попробуйте снова — /start")
            is IllegalStateException ->
                messageSender.sendTextMessage(chatId, "Этот слот уже занят. Выберите другое время.")
            else ->
                messageSender.sendTextMessage(chatId, "Что-то пошло не так. Попробуйте — /start")
        }
        stateManager.resetState(chatId)
    }
}