package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.CallbackData
import ru.hey_savvy.bookingBot.bot.router.chatId
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager
import ru.hey_savvy.bookingBot.bot.state.Step

@Component
class MasterHandler(
    private val stateManager: ConversationStateManager,
    private val bookingHandler: BookingHandler
) : Handler {

    override fun handle(update: Update) {
        val chatId = update.chatId()
        val data = update.callbackQuery.data

        when {
            data.startsWith(CallbackData.MASTER.prefix + ":") -> handleMasterChoice(chatId, data, update)
            data == CallbackData.SKIP_MASTER.prefix -> skipMaster(chatId, update)
        }
    }

    private fun handleMasterChoice(chatId: Long, data: String, update: Update) {
        val masterId = data.substringAfter(":").toLong()
        val state = stateManager.getState(chatId)
        stateManager.setState(
            chatId, state.copy(
                step = Step.CHOOSING_DATE,
                selectedMasterId = masterId
            )
        )
        bookingHandler.handle(update)
    }

    private fun skipMaster(chatId: Long, update: Update) {
        val state = stateManager.getState(chatId)
        stateManager.setState(
            chatId, state.copy(
                step = Step.CHOOSING_DATE,
                selectedMasterId = null
            )
        )
        bookingHandler.handle(update)
    }
}