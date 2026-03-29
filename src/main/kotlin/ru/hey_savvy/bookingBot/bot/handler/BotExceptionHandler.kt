package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import ru.hey_savvy.bookingBot.bot.MessageSender
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager
import ru.hey_savvy.bookingBot.messages.bookingNotFound
import ru.hey_savvy.bookingBot.messages.somethingWentWrong
import ru.hey_savvy.bookingBot.messages.timeSlotTaken

@Component
class BotExceptionHandler(
    private val messageSender: MessageSender,
    private val stateManager: ConversationStateManager
) {
    fun handle(e: Exception, chatId: Long) {
        when (e) {
            is NoSuchElementException ->
                messageSender.sendTextMessage(chatId, bookingNotFound)
            is IllegalStateException ->
                messageSender.sendTextMessage(chatId, timeSlotTaken)
            else ->
                messageSender.sendTextMessage(chatId, somethingWentWrong)
        }
        stateManager.resetState(chatId)
    }
}