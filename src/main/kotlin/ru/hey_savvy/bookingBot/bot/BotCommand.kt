package ru.hey_savvy.bookingBot.bot

enum class BotCommand (
    private val command: String,
) {
    START("/start"),
    CANCEL("/cancel"),
    MY_BOOKINGS("/my_bookings");

    companion object {
        fun fromText(text: String) : BotCommand? =
            entries.find { it.command == text }
    }

}