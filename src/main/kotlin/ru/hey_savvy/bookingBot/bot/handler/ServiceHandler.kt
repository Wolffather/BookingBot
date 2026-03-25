package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.CallbackData
import ru.hey_savvy.bookingBot.bot.MessageSender
import ru.hey_savvy.bookingBot.bot.keyboard.masterKeyboard
import ru.hey_savvy.bookingBot.bot.keyboard.servicesKeyboard
import ru.hey_savvy.bookingBot.bot.router.chatId
import ru.hey_savvy.bookingBot.bot.state.ConversationState
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager
import ru.hey_savvy.bookingBot.bot.state.Step
import ru.hey_savvy.bookingBot.service.ServiceToBookService

@Component
class ServiceHandler(
    private val messageSender: MessageSender,
    private val serviceToBookService: ServiceToBookService,
    private val stateManager: ConversationStateManager
) : Handler
{
    override fun handle(update: Update) {
        val chatId = update.chatId()
        val data = update.callbackQuery.data

        when {
            data == CallbackData.BOOKING_START.prefix -> showServices(chatId)
            data.startsWith(CallbackData.SERVICE.prefix) -> handleServiceChoice(chatId, data)
        }
    }

    private fun showServices(chatId: Long) {
        val services = serviceToBookService.findAvailableServices()
        val keyboard = servicesKeyboard(services)
        messageSender.sendMessageWithButtons(chatId, "Выберите услугу:", keyboard)

        stateManager.setState(chatId, ConversationState(step = Step.CHOOSING_SERVICE))
    }

    private fun handleServiceChoice(chatId: Long, data: String) {
        val serviceId = data.substringAfter(":").toLong()

        stateManager.setState(chatId, ConversationState(
            step = Step.CHOOSING_MASTER,
            selectedServiceId = serviceId
        ))

        val masters = serviceToBookService.getMastersForService(serviceId)
        val keyboard = masterKeyboard(masters)
        messageSender.sendMessageWithButtons(chatId, "Выберите мастера:", keyboard)
    }
}