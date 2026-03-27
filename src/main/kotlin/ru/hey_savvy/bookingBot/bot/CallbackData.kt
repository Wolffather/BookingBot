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
    MASTER("MASTER"),
    SKIP_MASTER("SKIP_MASTER"),
    CANCEL_BOOKING("CANCEL_BOOKING");

    companion object {
        fun fromData(data: String) : CallbackData? =
            entries.find { it.prefix == data || data.startsWith(it.prefix + ":") }
    }
}
