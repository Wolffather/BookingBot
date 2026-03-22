package ru.hey_savvy.bookingBot.bot

enum class CallbackData(
    val prefix : String,
) {
    BOOKING_START("BOOKING_START"),
    CONFIRM("CONFIRM"),
    CANCEL_FLOW("CANCEL_FLOW"),
    SERVICE("SERVICE"),
    DATE("DATE"),
    SLOT("SLOT"),
    CANCEL_BOOKING("CANCEL_BOOKING");

    companion object {
        fun fromPrefix(prefix: String) : CallbackData? =
            CallbackData.entries.find { it.prefix == prefix }
    }
}
