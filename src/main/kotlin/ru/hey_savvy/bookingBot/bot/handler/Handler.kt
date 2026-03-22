package ru.hey_savvy.bookingBot.bot.handler

interface Handler {

    fun handle(chatId: Long)
}