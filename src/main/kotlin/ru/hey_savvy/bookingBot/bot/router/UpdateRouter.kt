package ru.hey_savvy.bookingBot.bot.router

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.BotCommand
import ru.hey_savvy.bookingBot.bot.CallbackData
import ru.hey_savvy.bookingBot.bot.handler.BookingHandler
import ru.hey_savvy.bookingBot.bot.handler.CancelHandler
import ru.hey_savvy.bookingBot.bot.handler.MasterHandler
import ru.hey_savvy.bookingBot.bot.handler.MyBookingsHandler
import ru.hey_savvy.bookingBot.bot.handler.ServiceHandler
import ru.hey_savvy.bookingBot.bot.handler.StartHandler
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager
import ru.hey_savvy.bookingBot.bot.state.Step

@Component
class UpdateRouter(
    private val stateManager: ConversationStateManager,
    private val startHandler: StartHandler,
    private val serviceHandler: ServiceHandler,
    private val masterHandler: MasterHandler,
    private val bookingHandler: BookingHandler,
    private val myBookingsHandler: MyBookingsHandler,
    private val cancelHandler: CancelHandler,

    ) {
    fun route(update: Update) {

        if (update.hasMessage() && update.message.hasText()) {
            val command = BotCommand.fromText(update.message.text)
            if (command != null) {
                routeCommand(command, update)
                return
            }
            routeText(update)
            return
        }
        if (update.hasCallbackQuery()) {
            routeCallback(update)
            return
        }

    }

    private fun routeCommand(botCommand: BotCommand, update: Update) {
        when (botCommand) {
            BotCommand.START -> startHandler.handle(update)
            BotCommand.MY_BOOKINGS -> myBookingsHandler.handle(update)
            BotCommand.CANCEL -> cancelHandler.handle(update)
        }
    }

    private fun routeCallback(update: Update) {
        val callback = CallbackData.fromData(update.callbackQuery.data)

        when (callback) {
            CallbackData.BOOKING_START, CallbackData.SERVICE -> serviceHandler.handle(update)

            CallbackData.DATE, CallbackData.SLOT, CallbackData.CONFIRM, CallbackData.CANCEL_FLOW -> bookingHandler.handle(
                update
            )

            CallbackData.CANCEL_BOOKING -> myBookingsHandler.handle(update)

            CallbackData.MASTER, CallbackData.SKIP_MASTER -> masterHandler.handle(update)

            null -> Unit
        }
    }

    private fun routeText(update: Update) {
        val chatId = update.chatId()
        val state = stateManager.getState(chatId)

        when (state.step) {
            Step.IDLE -> {}

            Step.CHOOSING_DATE, Step.CHOOSING_TIME, Step.CONFIRMING -> bookingHandler.handle(update)

            Step.CHOOSING_SERVICE -> serviceHandler.handle(update)
            Step.CHOOSING_MASTER -> masterHandler.handle(update)
        }
    }
}


fun Update.chatId(): Long = if (hasMessage()) message.chatId
else if (hasCallbackQuery()) callbackQuery.message.chatId
else throw IllegalStateException("Unknown update type")