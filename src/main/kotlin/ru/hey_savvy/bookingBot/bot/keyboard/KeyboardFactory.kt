package ru.hey_savvy.bookingBot.bot.keyboard

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.hey_savvy.bookingBot.bot.CallbackData
import ru.hey_savvy.bookingBot.models.Master
import ru.hey_savvy.bookingBot.models.ServiceToBook
import ru.hey_savvy.bookingBot.util.toDisplayString
import java.time.LocalDate
import java.time.LocalTime


fun mainMenuKeyboard(): InlineKeyboardMarkup  = constructKeyboard(
    data = listOf("Записаться"),
    buttonText = { "Записаться" },
    callbackData = { CallbackData.BOOKING_START.prefix }
)

fun servicesKeyboard(serviceToBooks: List<ServiceToBook>): InlineKeyboardMarkup = constructKeyboard(
    data = serviceToBooks,
    buttonText = { it.title },
    callbackData = { CallbackData.SERVICE.prefix + ":" + it.id }
)


fun masterKeyboard(masters: List<Master>): InlineKeyboardMarkup {
    val masterRows = constructKeyboard(
        data = masters,
        buttonText = { it.name },
        callbackData = { CallbackData.MASTER.prefix + ":" + it.id }
    ).keyboard

    val skipRow = listOf(InlineKeyboardButton("Не важно")
        .apply { callbackData = CallbackData.SKIP_MASTER.prefix })

    return InlineKeyboardMarkup(masterRows + listOf(skipRow))
}


fun datesKeyboard(dates: List<LocalDate>): InlineKeyboardMarkup = constructKeyboard(
    data = dates,
    buttonText = { it.toDisplayString() },
    callbackData = { CallbackData.DATE.prefix + ":" + it.toString() },
    rowSize = 3
)

fun timeSlotsKeyboard(timeSlots: List<LocalTime>): InlineKeyboardMarkup = constructKeyboard(
    data = timeSlots,
    buttonText = { it.toDisplayString() },
    callbackData = { CallbackData.SLOT.prefix + ":" + it.toString() },
    rowSize = 3
)

fun confirmationsKeyboard(): InlineKeyboardMarkup {
    val buttons = listOf(
        InlineKeyboardButton("Подтвердить")
            .apply { callbackData = CallbackData.CONFIRM.prefix },
        InlineKeyboardButton("Отмена")
            .apply { callbackData = CallbackData.CANCEL_FLOW.prefix }
    )

    return InlineKeyboardMarkup(listOf(buttons))
}

private fun <T> constructKeyboard(
    data: List<T>,
    buttonText: (T) -> String,
    callbackData: (T) -> String,
    rowSize: Int = data.size
): InlineKeyboardMarkup {
    val buttons = data.map { item ->
        InlineKeyboardButton(buttonText(item))
            .apply { this.callbackData = callbackData(item) }
    }
    return InlineKeyboardMarkup(buttons.chunked(rowSize))
}
