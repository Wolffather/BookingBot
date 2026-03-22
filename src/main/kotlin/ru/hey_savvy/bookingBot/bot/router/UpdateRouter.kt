package ru.hey_savvy.bookingBot.bot.router

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.hey_savvy.bookingBot.bot.BotCommand
import ru.hey_savvy.bookingBot.bot.MessageSender
import ru.hey_savvy.bookingBot.bot.handler.ServiceHandler
import ru.hey_savvy.bookingBot.bot.handler.StartHandler
import ru.hey_savvy.bookingBot.bot.state.ConversationStateManager
import ru.hey_savvy.bookingBot.bot.state.Step
import kotlin.ranges.step

@Component
class UpdateRouter(
    private val stateManager: ConversationStateManager,
    //private val serviceHandler: ServiceHandler,
    private val startHandler: StartHandler,
//    private val bookingHandler: BookingHandler,
//    private val myBookingsHandler: MyBookingsHandler,
//    private val cancelHandler: CancelHandler,
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
        val chatId = update.chatId()
        when (botCommand) {
            BotCommand.START -> startHandler.handle(chatId)
            BotCommand.MY_BOOKINGS -> {}
            BotCommand.CANCEL -> {}
        }
    }

    private fun routeCallback(update: Update) {}

    private fun routeText(update: Update) {
        val chatId = update.chatId()
        val state = stateManager.getState(chatId)

        when (state.step) {
            Step.IDLE -> {}
            Step.CHOOSING_DATE -> {}
            Step.CHOOSING_TIME -> {}
            Step.CHOOSING_SERVICE -> {}
            Step.CONFIRMING -> {}
        }
    }
}


fun Update.chatId(): Long = if (hasMessage()) message.chatId
else if (hasCallbackQuery()) callbackQuery.message.chatId
else throw IllegalStateException("Unknown update type")