package ru.hey_savvy.bookingBot.bot.state

import java.time.LocalDate

data class ConversationState(
    val step: Step = Step.IDLE,
    val selectedServiceId: Long? = null,
    val selectedMasterId: Long? = null,
    val selectedDate: LocalDate? = null,
    val selectedSlotId: Long? = null
)