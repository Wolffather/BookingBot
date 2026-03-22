package ru.hey_savvy.bookingBot.bot.state

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class ConversationStateManager {
    private val states = ConcurrentHashMap<Long, ConversationState>()

    fun getState(chatId: Long): ConversationState =
        states.getOrDefault(chatId, ConversationState())

    fun setState(chatId: Long, state: ConversationState) {
        states[chatId] = state
    }

    fun resetState(chatId: Long) {
        states.remove(chatId)
    }
}

