package ru.hey_savvy.bookingBot.bot.keyboard

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


fun mainMenuKeyboard(): InlineKeyboardMarkup {
    val mainMenuButton = InlineKeyboardButton("Главное меню")
        .apply { callbackData = "HELLO" }
    return InlineKeyboardMarkup(listOf(listOf(mainMenuButton)))
}
