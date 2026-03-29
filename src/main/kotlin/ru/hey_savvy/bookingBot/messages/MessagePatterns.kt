package ru.hey_savvy.bookingBot.messages

import ru.hey_savvy.bookingBot.models.Booking
import ru.hey_savvy.bookingBot.util.toDisplayString

fun bookingInfo(booking: Booking) =
    """
        Вы записаны на ${booking.serviceToBook.title}
        ${booking.timeSlot.startAt.toDisplayString()}
        К мастеру ${booking.timeSlot.master.name}
    """.trimIndent()

const val book = "Записаться"
const val noActiveBookings = "У Вас нет активных записей"
const val bookingCancelled = "Запись отменена"
const val bookingNotFound = "Запись не найдена. Попробуйте снова — /start"
const val timeSlotTaken = "Этот слот уже занят. Выберите другое время."
const val somethingWentWrong = "Что-то пошло не так. Попробуйте — /start"
const val bookingConfirmationRequest = "Подтверждаете запись?"
const val chooseService = "Выберите услугу"
const val chooseMaster = "Выберите мастера"
const val skipMaster = "Не важно"

const val confirmButtonText = "Подтвердить"
const val cancelButtonText = "Отменить"