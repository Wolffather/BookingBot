package ru.hey_savvy.bookingBot.bot.handler

import org.telegram.telegrambots.meta.api.objects.Update

interface Handler {

    fun handle(update: Update)
}