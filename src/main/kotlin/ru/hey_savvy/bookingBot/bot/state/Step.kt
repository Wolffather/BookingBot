package ru.hey_savvy.bookingBot.bot.state

enum class Step {
    IDLE,
    CHOOSING_SERVICE,
    CHOOSING_DATE,
    CHOOSING_TIME,
    CONFIRMING
}