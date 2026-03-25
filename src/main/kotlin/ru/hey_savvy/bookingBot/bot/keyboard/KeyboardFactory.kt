package ru.hey_savvy.bookingBot.bot.keyboard

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.hey_savvy.bookingBot.bot.CallbackData
import ru.hey_savvy.bookingBot.models.Master
import ru.hey_savvy.bookingBot.models.ServiceToBook


fun mainMenuKeyboard(): InlineKeyboardMarkup {
    val mainMenuButton = InlineKeyboardButton("Записаться")
        .apply { callbackData = CallbackData.BOOKING_START.prefix }
    return InlineKeyboardMarkup(listOf(listOf(mainMenuButton)))
}

fun servicesKeyboard(serviceToBooks: List<ServiceToBook>): InlineKeyboardMarkup {
    val serviceButtons = serviceToBooks
        .map { InlineKeyboardButton(it.title)
            .apply { callbackData = CallbackData.SERVICE.prefix + ":" + it.id } }
    return InlineKeyboardMarkup(listOf(serviceButtons))
}

fun masterKeyboard(masters: List<Master>): InlineKeyboardMarkup {
    val masterButtons = masters
        .map { InlineKeyboardButton(it.name)
            .apply { callbackData = CallbackData.MASTER.prefix + ":" + it.id } }

    val skipButton = InlineKeyboardButton("Не важно")
        .apply { callbackData = CallbackData.SKIP_MASTER.prefix }

    return InlineKeyboardMarkup(listOf(masterButtons + skipButton))
}
