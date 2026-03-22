package ru.hey_savvy.bookingBot.bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.Serializable

@Component
class MessageSender() {

    lateinit var sender: AbsSender

    fun sendTextMessage(chatId: Long, text: String) =
        execute {
            configureSender(chatId, text)
        }

    fun sendMessageWithButtons(chatId: Long, text: String, keyboard: InlineKeyboardMarkup) =
        execute {
            configureSender(chatId, text).apply { replyMarkup = keyboard }
        }

    fun answerCallback(callbackQueryId: String) =
        execute {
            configureAnswerCallbackQuery(callbackQueryId)
        }


    private fun configureSender(chatId: Long, text: String): SendMessage =
        SendMessage().apply {
            this.chatId = chatId.toString()
            this.text = text
        }

    private fun configureAnswerCallbackQuery(callbackQueryId: String): AnswerCallbackQuery =
        AnswerCallbackQuery().apply { this.callbackQueryId = callbackQueryId }

    private fun<T: Serializable> execute(block: () -> BotApiMethod<T>) {
        try {
            sender.execute(block())
        } catch (e: TelegramApiException) {
            e.printStackTrace() //TODO: handle exception
        }
    }
}