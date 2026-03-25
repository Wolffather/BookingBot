package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.CallbackData
import ru.hey_savvy.bookingBot.bot.MessageSender
import ru.hey_savvy.bookingBot.bot.router.chatId
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager
import ru.hey_savvy.bookingBot.bot.state.Step
import ru.hey_savvy.bookingBot.service.ServiceToBookService

@Component
class MasterHandler(
    private val serviceToBookService: ServiceToBookService,
    private val stateManager: ConversationStateManager,
    private val messageSender: MessageSender,
) : Handler {

    override fun handle(update: Update) {
        val chatId = update.chatId()
        val data = update.callbackQuery.data

        when {
            data.startsWith(CallbackData.MASTER.prefix + ":") -> handleMasterChoice(chatId, data)
            data == CallbackData.SKIP_MASTER.prefix -> skipMaster(chatId)
        }
    }

    private fun handleMasterChoice(chatId: Long, data: String) {
        val masterId = data.substringAfter(":").toLong()
        val state = stateManager.getState(chatId)
        stateManager.setState(chatId, state.copy(
            step = Step.CHOOSING_DATE,
            selectedMasterId = masterId
        ))
        messageSender.sendTextMessage(chatId, "Мастер выбран. Теперь выберите дату.")
    }

    private fun skipMaster(chatId: Long) {
        val state = stateManager.getState(chatId)
        stateManager.setState(chatId, state.copy(
            step = Step.CHOOSING_DATE,
            selectedMasterId = null
        ))
        messageSender.sendTextMessage(chatId, "Хорошо, подберём любого мастера. Выберите дату.")
    }
}