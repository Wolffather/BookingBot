package ru.hey_savvy.bookingBot.bot.handler

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.CallbackData
import ru.hey_savvy.bookingBot.bot.MessageSender
import ru.hey_savvy.bookingBot.bot.keyboard.datesKeyboard
import ru.hey_savvy.bookingBot.bot.keyboard.timeSlotsKeyboard
import ru.hey_savvy.bookingBot.bot.router.chatId
import ru.hey_savvy.bookingBot.bot.state.ConversationState
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager
import ru.hey_savvy.bookingBot.bot.state.Step
import ru.hey_savvy.bookingBot.service.ServiceToBookService
import ru.hey_savvy.bookingBot.service.TimeSlotService
import ru.hey_savvy.bookingBot.util.toDisplayString
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class BookingHandler(
    private val messageSender: MessageSender,
    private val stateManager: ConversationStateManager,
    private val timeSlotService: TimeSlotService,
    private val serviceToBookService: ServiceToBookService,
) : Handler {
    override fun handle(update: Update) {
        val chatId = update.chatId()
        val state = stateManager.getState(chatId)
        val data = update.callbackQuery?.data

        when {
            state.step == Step.CHOOSING_DATE && data?.startsWith(CallbackData.DATE.prefix + ":") != true ->
                showAvailableDates(chatId, state)
            data?.startsWith(CallbackData.DATE.prefix + ":") == true ->
                handleDateChoice(chatId, data)
            data?.startsWith(CallbackData.SLOT.prefix + ":") == true ->
                handleSlotChoice(chatId, state, data)
        }
    }

    private fun showAvailableDates(chatId: Long, state: ConversationState) {
        val selectedMaster = state.selectedMasterId
        val availableDates = timeSlotService.findAvailableDates(selectedMaster)
        val keyboard = datesKeyboard(availableDates)

        messageSender.sendMessageWithButtons(chatId, "Выберите дату", keyboard)
    }

    private fun handleDateChoice(chatId: Long, data: String) {
        val date = LocalDate.parse(data.substringAfter(":"))
        val state = stateManager.getState(chatId)
        stateManager.setState(chatId, state.copy(
            step = Step.CHOOSING_TIME,
            selectedDate = date
        ))
        showAvailableSlots(chatId, stateManager.getState(chatId))
    }
    private fun showAvailableSlots(chatId: Long, state: ConversationState) {
        val date = state.selectedDate
            ?: throw IllegalStateException("Date not selected")
        val masterId = state.selectedMasterId
        val availableTime = timeSlotService.findAvailableTimeSlots(date, masterId)
        val keyboard = timeSlotsKeyboard(availableTime)

        messageSender.sendMessageWithButtons(chatId, "Выберите время", keyboard)
    }
    private fun handleSlotChoice(chatId: Long, state: ConversationState, data: String) {
        val time = LocalTime.parse(data.substringAfter(":"))
        val date = state.selectedDate
            ?: throw IllegalStateException("Date not selected")
        val slotId = timeSlotService.getSlotId(LocalDateTime.of(date, time))
        stateManager.setState(chatId, state.copy(
            step = Step.CONFIRMING,
            selectedSlotId = slotId
        ))

        showConfirmation(chatId, stateManager.getState(chatId))
    }
    private fun showConfirmation(chatId: Long, state: ConversationState) {
        val serviceId = state.selectedServiceId ?: throw IllegalStateException("Service not selected")
        val slotId = state.selectedSlotId ?: throw IllegalStateException("SlotId not selected")

        val service = serviceToBookService.getServiceTitle(serviceId)
        val master = timeSlotService.getMasterName(slotId) ?: "Не выбран"
        val slot = timeSlotService.getSlot(slotId).toDisplayString()

        val text =
            """
                Вы записаны на $service
                $slot
                К мастеру $master
            """.trimIndent()
        messageSender.sendTextMessage(chatId, text)
    }
}